package server;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

import client.gui.LobbyPanel;
import client.gui.chess.BoardState;
import protocol.ChatProtocol;
import protocol.GameProtocol;
import protocol.LobbyProtocol;
import protocol.Protocol;
import protocol.RoomProtocol;
import common.Sender;
import common.Util;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.*;
import common.UserInfo;


class ServerReceiver extends Thread
{
    private ObjectInputStream in = null;
    
    private Socket socket = null;
    private String id = null;
    private Lobby lobby = null;
    private Sender sender = null; 
    private ServerInterface server = null; 
    
    private int userLocation = 0;
    private boolean isOwner = false;
    private boolean isReallyWhiteTurn= false;
    
    ServerReceiver( Socket socket, Lobby lobby ) throws IOException{
        sender = new Sender( socket );
        in = new ObjectInputStream( socket.getInputStream() );
        this.lobby = lobby;
        this.socket = socket;
        
        userLocation = ServerInterface.LOBBY; // 초기 위치는 로비
        server = lobby;                       // 속한 위치가 로비
        
        Util.println( "ServerReceiver\tnew ServerReceiver\t\t\t"+getInfo() );
    }

    String getInfo() {
        return "["+id+"] ["+socket.getInetAddress()+":"+socket.getPort()+"]";
    }
    Sender getSender() {
        return sender;
    }
    private void close() {
        Util.println( "ServerReceiver\tconnection closed\t\t\t"+getInfo() );
        this.interrupt();
    }
    String getID() {
        return id;
    }
    
    public void setSender(Sender sender) {
		this.sender = sender;
	}

	public void run() {
        Protocol p = null;
        try {
            // 테스트
            //
            //
            p = ( Protocol )in.readObject();
            id = ((ChatProtocol)p).getData();
            in.readObject();
            lobby.addUser( this );
            server.broadcast( new LobbyProtocol( LobbyProtocol.ENTER_LOBBY, id ) );
            sender.send( new LobbyProtocol( LobbyProtocol.USER_LIST, lobby.getUserList() ));
            sender.send( new LobbyProtocol( LobbyProtocol.ROOM_LIST, lobby.getRoomList() ));
            
            //
            //
            while( !Thread.currentThread().isInterrupted() ) {
                p = ( Protocol )in.readObject();
                if( p instanceof ChatProtocol ) 
                    handleProtocol( (ChatProtocol)p );
                
                else if( p instanceof LobbyProtocol ) 
                    handleProtocol( (LobbyProtocol)p );
                
                else if( p instanceof RoomProtocol )
                    handleProtocol( (RoomProtocol)p );
                
                else if( p instanceof GameProtocol )
                    handleProtocol( (GameProtocol)p );
            }
        }
        catch( SocketException e ) {
            close();
            if( server instanceof Lobby ) {
                server.broadcast( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, id ) );
                server.removeUser( id );
            }
            else {
                //if( isInGame )
                // 기권처리
                // else
                server.removeUser( id );
                server.broadcast( new RoomProtocol( RoomProtocol.EXIT_ROOM, id ) );
                // 방 참여 인원이 없는 경우 방 삭제
                int roomNum = ((Room)server).getRoomNumber();
                if( ((Room)server).getSize() == 0 ) {
                    server = lobby;
                    ((Lobby)server).deleteRoom( roomNum );
                    server.broadcast( new LobbyProtocol( LobbyProtocol.DELETE_ROOM, roomNum ) );
                }
                else {
                    if( isOwner ) {
                        ((Room)server).getReceivers().next().getSender().send( new RoomProtocol( RoomProtocol.OWNER ) );
                        isOwner = false;
                    }
                    server = lobby;
                    server.broadcast( new LobbyProtocol( LobbyProtocol.ROOM_STATE_WAITING, roomNum ) );
                }
            }
        }
        catch( IOException | ClassNotFoundException e ) {
            e.printStackTrace();
            System.out.println("fail to read data");
        }

    }
    
    private void handleProtocol( ChatProtocol p ) {
        //System.out.println( "get Chat Protocol" );
        switch( p.getProtocol() ) {
        
        case ChatProtocol.MESSAGE:
            p.setData( id + " : " + p.getData() + "\n" );
            server.broadcast( p );
            break;
            
        case ChatProtocol.WHISPER:
            Sender s = lobby.getSender( p.getID() );
            if( s == null ) {
                sender.send( new ChatProtocol( ChatProtocol.SYSTEM, "[SYSTEM] 접속하지 않았거나 존재하지 않는 대상입니다.\n" ) );
            }
            else if( s.equals( sender ) ) {
                sender.send( new ChatProtocol( ChatProtocol.SYSTEM, "[SYSTEM] 자기 자신에게 보낼 수 없습니다.\n" ) );
            }
            else {
                p.setData( id + " : " + p.getData() + "\n" );
                sender.send( p );
                s.send( p );
            }
            break;
            
        case ChatProtocol.QUIT:
            // 종료처리
            close();
            sender.send( p );
            server.broadcast( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, id ) );
            server.removeUser( id );
            break;
            
        case LobbyProtocol.REQUEST_USER_INFO:
	        {
	        	String sid = (String)p.getData();
	        	Statement stmt = null;
	        	String win = null;
	        	String lose = null;
	        	try
	        	{
	        		Class.forName("com.mysql.jdbc.Driver");
	        		Connection conn =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/member", "root", "1234");
	        		stmt = conn.createStatement();
	        		ResultSet rs = stmt.executeQuery("select win,lose from guest where id in ('ljy03')");
	        		while(rs.next())
	        		{
	        			try {
							win = new String(rs.getString("win").getBytes("ISO-8859-1"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			try {
							lose = new String(rs.getString("lose").getBytes("ISO-8859-1"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			sender.send( new LobbyProtocol( LobbyProtocol.REQUEST_USER_INFO, new UserInfo( win, lose ) ) );
	        		}  		
	        	}
	        	catch (ClassNotFoundException e)
	        	{
	        		e.printStackTrace();
	        	} 
	        	catch(SQLException e)
	        	{
	        		e.printStackTrace();
	        	}
	        	
	        	break;
	        }
        }
    }
    
    private void handleProtocol( LobbyProtocol p ) {
        //System.out.println( "get Lobby Protocol" );
        switch( p.getProtocol() ) {
        case LobbyProtocol.ENTER_LOBBY:
            server.broadcast( p );
            break;
        
        case LobbyProtocol.EXIT_LOBBY:
            server.broadcast( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, id ) );
            server.removeUser( id );
            break;
            
        case LobbyProtocol.CREATE_ROOM:
        {
            Room room = new Room( (String)p.getName(), (Integer)p.getData() );
            if( lobby.addRoom( (Room)room, (Integer)p.getData() ) == false ) {
                sender.send( new LobbyProtocol( LobbyProtocol.REJECT_CREATE_ROOM, null ) );
                break;
            }
            userLocation = (Integer)p.getData();
            isOwner = true;
            isReallyWhiteTurn = true;
            server = room;
            sender.send( p );
            server.addUser( lobby.removeUser( id ) );
            p.setProtocol( LobbyProtocol.ADD_ROOM );
            lobby.broadcast( p );
            lobby.broadcast( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, id ) );
            ((Room)server).setOwner( id );
            ((Room)server).setOwnerSender(sender);
            break;
        }
            
        case LobbyProtocol.ENTER_ROOM:
            Room room = lobby.getRoom( (Integer)p.getData() );
            if( room != null ) {
                if( room.getSize() == 1 ) {
                    userLocation = (Integer)p.getData();
                    server.broadcast( new LobbyProtocol( LobbyProtocol.ROOM_STATE_FULL, p.getData() ) );
                    server.broadcast( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, id ) );
                    server = room;
                    server.addUser( lobby.removeUser( id ) );
                    ((Room)server).setGuestSender(sender);
                    sender.send( p );
                }
                else {
                    sender.send( new LobbyProtocol( 
                                 LobbyProtocol.REJECT_ENTER_ROOM, null ));
                }
            }
            else {
                sender.send( new LobbyProtocol(
                             LobbyProtocol.CREATE_ROOM, p.getData() ));
            }
            break;
        }
    }
    
    private void handleProtocol( RoomProtocol p ) {
        //System.out.println( "get Room Protocol" );
        switch( p.getProtocol() ) {
        case RoomProtocol.START:
            
            break;
            
        case RoomProtocol.READY:
        	if(((Room)server).isInGame() == false){
        		
        		if( ((Room)server).getSize() < 2) {
            		sender.send( new ChatProtocol( ChatProtocol.NOTICE, "도전자를 기다리는 중 입니다.\n" ) );
	            }else{
	            	if(isOwner){
	            		
	            		if(((Room)server).isReady() == false){
	            			server.broadcast( new ChatProtocol( ChatProtocol.NOTICE, "도전자가 준비되지 않았습니다.\n" ) );
	            		}else{
	            			((Room)server).setisInGame(true);
	            			server.broadcast( new ChatProtocol( ChatProtocol.NOTICE, "게임시작!\n" ) );
	            			server.broadcast(new GameProtocol(GameProtocol.GAME_START));
	            		}
	            		
	            	}else{
	            		
	            		if(((Room)server).isReady() == false){
	            			((Room)server).setReady(true);
	                		server.broadcast( new ChatProtocol( ChatProtocol.NOTICE, "도전자가 준비되었습니다!\n" ) );
	            		}else{
	            			
	            		}
	            	}
	            }
        	}else{
        		sender.send( new ChatProtocol( ChatProtocol.SYSTEM, "[SYSTEM] 게임중입니다.\n" ) );
        	}
            
            break;
            
        case RoomProtocol.CANCLE:
            
            break;
            
        case RoomProtocol.ENTER_ROOM:
            server.broadcast( new ChatProtocol( 
                    ChatProtocol.NOTICE, id+"님이 참가하셨습니다.\n" ));
            sender.send( new ChatProtocol(
                    ChatProtocol.NOTICE, "현재 차례 당 시간제한은 "+((Room)server).getTurnTime()+"초 입니다.\n" ));
            if( ((Room)server).getSize() == 2 ) {
                sender.send( new RoomProtocol( RoomProtocol.ENTER_ROOM, ((Room)server).getOwner() ));
            }
            server.broadcast( p );
            break;
            
        case RoomProtocol.EXIT_ROOM:
            server.removeUser( id );
            server.broadcast( new RoomProtocol( RoomProtocol.EXIT_ROOM, id ) );
            // 방 참여 인원이 없는 경우 방 삭제
            int roomNum = ((Room)server).getRoomNumber();
            if( ((Room)server).getSize() == 0 ) {
                server = lobby;
                ((Lobby)server).deleteRoom( roomNum );
                server.broadcast( new LobbyProtocol( LobbyProtocol.DELETE_ROOM, roomNum ) );
            }
            else {
                if( isOwner ) {
                    ((Room)server).getReceivers().next().getSender().send( new RoomProtocol( RoomProtocol.OWNER ) );
                    isOwner = false;
                    isReallyWhiteTurn = false;
                }
                ((Room)server).setisInGame(false);
                ((Room)server).setReady(false);
                server = lobby;
                server.broadcast( new LobbyProtocol( LobbyProtocol.ROOM_STATE_WAITING, roomNum ) );
            }
            
            userLocation = ServerInterface.LOBBY;
            server.addUser( this );
            server.broadcast( new LobbyProtocol( LobbyProtocol.ENTER_LOBBY, id ) );
            sender.send( new LobbyProtocol( LobbyProtocol.USER_LIST, lobby.getUserList() ) );
            sender.send( new LobbyProtocol( LobbyProtocol.ROOM_LIST, lobby.getRoomList() ) );
            break;
            
        case RoomProtocol.REQUEST_INVITATION_USER_LIST:
            if( lobby.getRoom( userLocation ).getSize() != 2 ) {
                sender.send( new RoomProtocol( RoomProtocol.INVITATION_USER_LIST, lobby.getUserList() ) );
            }
            else {
                sender.send( new RoomProtocol( RoomProtocol.REJECT_REQUEST_INVITATION_USER_LIST ) );
            }
            break;
            
        case RoomProtocol.INVITE:
            Sender s = lobby.getSender( (String)p.getData() );
            if( s == null ) {
                sender.send( new RoomProtocol( RoomProtocol.REJECT_INVITATION ));
            }
            else if( !lobby.isInLobby( (String)p.getData() ) ) {
                sender.send( new RoomProtocol( RoomProtocol.REJECT_INVITATION ));
            }
            else {
                s.send( new RoomProtocol( RoomProtocol.INVITE, p.getData(), userLocation ) );
            }
            break;
            
        case RoomProtocol.OWNER:
            isOwner = true;
            isReallyWhiteTurn = true;
            ((Room)server).setOwner( id );
            break;
        }
        
        
            
    }
    
    private void handleProtocol( GameProtocol p ) {
        //System.out.println( "get Game Protocol" );
        switch( p.getProtocol() ) {
        
        case GameProtocol.GAME_MOVE:
        	if(((Room)server).isInGame() == true){
	        	if(p.isbooleanValue() == isReallyWhiteTurn){
	           server.broadcast(new GameProtocol(GameProtocol.GAME_MOVE, p.getData()));
	        	}
	        	else{
	        		sender.send( new ChatProtocol( ChatProtocol.NOTICE, "지금은 상대방 턴입니다!\n" ) );
	        	}
        	}
           break;
        case GameProtocol.GAME_GIVE_UP:
        	if(((Room)server).isInGame() == true){
        		
        		((Room)server).setisInGame(false);
    	    	((Room)server).setReady(false);
    	    	
    	    	if(isOwner){
    	    		sender.send(new GameProtocol(GameProtocol.GAME_LOSE));
    	    		((Room)server).getGuestSender().send(new GameProtocol(GameProtocol.GAME_WIN));
    	    	}else{
    	    		sender.send(new GameProtocol(GameProtocol.GAME_LOSE));
    	    		((Room)server).getOwnerSender().send(new GameProtocol(GameProtocol.GAME_WIN));
    	    	}
    	    	
    	    	server.broadcast(new GameProtocol(GameProtocol.GAME_QUIT));
    	    	
        	}else
        		sender.send( new ChatProtocol( ChatProtocol.SYSTEM, "[SYSTEM] 게임 중이 아닙니다!\n" ) );
	    	
	    	break;
        case GameProtocol.GAME_UNDO:
        	if(((Room)server).isInGame() == true){

        	    	if(isOwner){
        	    		((Room)server).getGuestSender().send(new GameProtocol(GameProtocol.GAME_CONFIRM));
        	    	}else{
        	    		((Room)server).getOwnerSender().send(new GameProtocol(GameProtocol.GAME_CONFIRM));
        	    	}
        	    	
        	}else
        		sender.send( new ChatProtocol( ChatProtocol.SYSTEM, "[SYSTEM] 게임 중이 아닙니다!\n" ) );
           break;
        case GameProtocol.GAME_CONFIRM:
        	if((boolean)p.getData() == false){
        		server.broadcast( new ChatProtocol( 
                        ChatProtocol.NOTICE, "무르기가 거절되었습니다.\n" ));
        	}else{
        		server.broadcast( new ChatProtocol( 
                        ChatProtocol.NOTICE, "무르기가 수락되었습니다.\n" ));
        		server.broadcast(new GameProtocol(GameProtocol.GAME_UNDO));
        	}
        }
    }
    
    
}
