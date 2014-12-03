package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import protocol.ChatProtocol;
import protocol.GameProtocol;
import protocol.LobbyProtocol;
import protocol.Protocol;
import protocol.RoomProtocol;
import common.Sender;
import common.Util;


class ServerReceiver extends Thread
{
    private ObjectInputStream in = null;
    
    private Socket socket = null;
    private String id = null;
    private Lobby lobby = null;
    private Sender sender = null; 
    private ServerInterface server = null; 
    
    private int userLocation = 0;
    
    ServerReceiver( Socket socket, Lobby lobby ) throws IOException{
        sender = new Sender( socket );
        in = new ObjectInputStream( socket.getInputStream() );
        this.lobby = lobby;
        this.socket = socket;
        
        userLocation = ServerInterface.LOBBY; // �ʱ� ��ġ�� �κ�
        server = lobby;                       // ���� ��ġ�� �κ�
        
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
    
    public void run() {
        Protocol p = null;
        try {
            // �׽�Ʈ
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
            // �������� ó��
            //
            //
            //
            
            close();
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
                sender.send( new ChatProtocol( ChatProtocol.SYSTEM, "[SYSTEM] �������� �ʾҰų� �������� �ʴ� ����Դϴ�.\n" ) );
            }
            else if( s.equals( sender ) ) {
                sender.send( new ChatProtocol( ChatProtocol.SYSTEM, "[SYSTEM] �ڱ� �ڽſ��� ���� �� �����ϴ�.\n" ) );
            }
            else {
                p.setData( id + " : " + p.getData() + "\n" );
                sender.send( p );
                s.send( p );
            }
            break;
            
            // �־�� �ǳ� Ȯ��
        case ChatProtocol.QUIT:
            // ����ó��
            close();
            break;
        }
    }
    
    private void handleProtocol( LobbyProtocol p ) {
        //System.out.println( "get Lobby Protocol" );
        switch( p.getProtocol() ) {
        case LobbyProtocol.ENTER_LOBBY:
            server.broadcast( p );
            break;
        
        case LobbyProtocol.EXIT_LOBBY:
            lobby.removeUser( id );
            server.broadcast( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, id ) );
            break;
            
        case LobbyProtocol.CREATE_ROOM:
        {
            if( p.getData() == null ) {
                server.broadcast( new ChatProtocol( 
                                  ChatProtocol.NOTICE, id+"���� �����ϼ̽��ϴ�.\n" ));
                sender.send( new ChatProtocol(
                                  ChatProtocol.NOTICE, "���� ���� �� �ð������� ~�� �Դϴ�.\n" ));
                break;
            }
            Room room = new Room( (String)p.getName(), (Integer)p.getData() );
            if( lobby.addRoom( (Room)room, (Integer)p.getData() ) == false ) {
                sender.send( new LobbyProtocol( LobbyProtocol.REJECT_CREATE_ROOM, null ) );
                break;
            }
            userLocation = ServerInterface.IN_ROOM_OWNER;
            server = room;
            sender.send( p );
            server.addUser( lobby.removeUser( id ) );
            p.setProtocol( LobbyProtocol.ADD_ROOM );
            lobby.broadcast( p );
            lobby.broadcast( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, id ) );
            break;
        }
            
        case LobbyProtocol.ENTER_ROOM:
            if( p.getData() == null ) {
                server.broadcast( new ChatProtocol( 
                                  ChatProtocol.NOTICE, id+"���� �����ϼ̽��ϴ�.\n" ));
                sender.send( new ChatProtocol(
                                  ChatProtocol.NOTICE, "���� ���� �� �ð������� ~�� �Դϴ�.\n" ));
                //
                //
                // To do
                // ��������Ʈ, ���� ����
                //
                //
                //
                break;
            }
            Room room = lobby.getRoom( (Integer)p.getData() );
            if( room != null ) {
                if( room.getSize() == 1 ) {
                    userLocation = ServerInterface.IN_ROOM_GUEST;
                    server = room;
                    server.addUser( lobby.removeUser( id ) );
                    sender.send( p );
                    p.setProtocol( LobbyProtocol.EXIT_LOBBY );
                    p.setData( id );
                    lobby.broadcast( p );
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
            
            break;
            
        case RoomProtocol.CANCLE:
            
            break;
            
        case RoomProtocol.EXIT_ROOM:
            server = lobby;
            break;
        }
    }
    
    private void handleProtocol( GameProtocol p ) {
        //System.out.println( "get Game Protocol" );
        switch( p.getProtocol() ) {
        
        case GameProtocol.PROTOCOL:
            // do something
        }
    }
    

}
