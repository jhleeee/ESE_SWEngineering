package client;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.RunElement;

import client.gui.LobbyPanel;
import client.gui.MainFrame;
import client.gui.PanelInterface;
import client.gui.chess.BoardState;
import client.gui.chess.GUIRunner;
import protocol.*;
import server.Room;
import common.RoomInfo;
import common.Sender;
import common.UserInfo;

class ClientReceiver extends Thread
{
    private Sender sender = null;
    private ObjectInputStream in = null;
    
    private String id = null;
    private String win = null;
    private String lose = null;
    
    MainFrame frame = null;
    // 변수 필요하면 생성
    
    ClientReceiver( Socket socket ) throws IOException {
        sender = new Sender( socket );
        in = new ObjectInputStream( socket.getInputStream() );
        //
        // 테스트
        Scanner scn = new Scanner( System.in );
        id = scn.next();
        sender.send( new ChatProtocol( 0, id ) );
        
        //
        //
        frame = new MainFrame( sender );
        frame.setVisible( true );
        
        // 테스트
        sender.send( new ChatProtocol( 0, id ) );
    }
    
    Sender getSender() {
        return sender;
    }
    private void close() {
        this.interrupt();
        frame.close();
    }
    
    // 필요한 메소드 있으면 만들어
    
    
    public void run() {
        Protocol p = null;
        try {
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
            // 서버 종료
            frame.messagePopup( null, "서버가 종료되었습니다" );
            close();
        }
        catch( IOException | ClassNotFoundException e ) {
            e.printStackTrace();
            System.out.println("fail to read data");
        }

    }
    
    private void handleProtocol( ChatProtocol p ) {
        switch( p.getProtocol() ) {
        case ChatProtocol.MESSAGE:
            frame.printMessage( p.getData(), Color.BLACK );
            break;
            
        case ChatProtocol.WHISPER:
            frame.printMessage( p.getData(), Color.GRAY );
            break;
            
        case ChatProtocol.SYSTEM:
            frame.printMessage( p.getData(), Color.RED );
            break;
            
        case ChatProtocol.NOTICE:
            frame.printMessage( p.getData(), Color.BLUE );
            break;
            
        case ChatProtocol.QUIT:
            close();
            break;
        }
    }
    
    @SuppressWarnings("unchecked")
    private void handleProtocol( LobbyProtocol p ) {
        switch( p.getProtocol() ) {
        case LobbyProtocol.ENTER_LOBBY:
            frame.addUser( (String)p.getData() );
            break;
            
        case LobbyProtocol.CREATE_ROOM:
            // 이름이 null 인 경우 방 생성
            if( p.getName() == null ) {
                String name = frame.inputPopup( " ", "방 이름을 입력해주세요" );
                if( name != null ) {
                    // 방 이름을 입력했다면 서버로 전송
                    p.setName( name );
                    sender.send( p );
                }
            }
            // 이름이 있으면 방이 생성 된 것
            else {
                // 방 패널로 바꾸고
                frame.setPanel( PanelInterface.RoomPanel );
                frame.setButtonToStart();
                //frame.setRoomTitle();
                sender.send( new RoomProtocol( RoomProtocol.ENTER_ROOM, id ) );
            }
            break;
            
        case LobbyProtocol.REJECT_CREATE_ROOM:
            frame.messagePopup( null, "방을 생성 할 수 없습니다" );
            break;
            
        case LobbyProtocol.REQUEST_USER_INFO:
            UserInfo info = (UserInfo) p.getData();
            frame.setWinLabel( info.getWin() );
            frame.setLoseLabel( info.getLose());
            frame.setRateLabel( info.getRate());  
            
        case LobbyProtocol.ENTER_ROOM:
            frame.setPanel( PanelInterface.RoomPanel );
            //frame.setRoomTitle();
            frame.setButtonToReady();
            sender.send( new RoomProtocol( RoomProtocol.ENTER_ROOM, id ) );
            break;
            
        case LobbyProtocol.REJECT_ENTER_ROOM:
            frame.messagePopup( null, "방에 입장 할 수 없습니다" );
            break;
            
        case LobbyProtocol.ROOM_STATE_FULL:
            frame.setRoomStateToFull( (int)p.getData() );
            break;
            
        case LobbyProtocol.ROOM_STATE_WAITING:
            frame.setRoomStateToWaiting( (int)p.getData() );
            break;
            
        case LobbyProtocol.ROOM_STATE_IN_GAME:
            frame.setRoomStateToInGame( (int)p.getData() );
            break;
            
        case LobbyProtocol.ADD_ROOM:
            frame.addRoom( p.getName(), (Integer)p.getData() );
            break;
            
        case LobbyProtocol.DELETE_ROOM:
            frame.deleteRoom( (int)p.getData() );
            break;
            
        case LobbyProtocol.ROOM_LIST:
            frame.addRoomList( (Vector<RoomInfo>)p.getData() );
            break;
            
        case LobbyProtocol.USER_LIST:
            // 현재 로비에 접속 중인 사용자들의 아이디를 담은 리스트
            Vector<String> v = (Vector<String>)p.getData();
            // 자기 자신은 제외하고 GUI에 추가
            v.remove( id );
            frame.addUserList( v );
            break;
            
        case LobbyProtocol.EXIT_LOBBY:
            frame.removeUser( (String)p.getData() );
            break;
        }
    }
    
    @SuppressWarnings("unchecked")
    private void handleProtocol( RoomProtocol p ) {
        //System.out.println( "get Room Protocol" );
        // 클라이언트 동작
        // 방에서 게임 전에 가능한 것, 준비, 준비취소, 시작 등
        switch( p.getProtocol() ) {
        case RoomProtocol.INVITATION_USER_LIST:
            frame.inviteList((Vector<String>) p.getData());
            break;
            
        case RoomProtocol.REJECT_INVITATION:
            frame.messagePopup( null, "해당 유저를 초대할 수 없습니다" );
            break;
            
        case RoomProtocol.REJECT_REQUEST_INVITATION_USER_LIST:
            frame.messagePopup( null, "초대할 수 없습니다" );
            break;
            
        case RoomProtocol.INVITE:
            if( frame.confirmPopup( null, p.getData() + "님이 " + p.getNumber() + "번 방에서 초대하셨습니다" ) ) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, p.getNumber() ));
            }
            else {
                
            }
            break;  
            
        case RoomProtocol.EXIT_ROOM:
            // 방에서 사람 나감
            frame.removeUser( (String)p.getData() );
            frame.printMessage( p.getData()+"님이 나가셨습니다.\n", Color.BLUE );
            break;
            
        case RoomProtocol.OWNER:
            frame.switchOwner();
            frame.printMessage( "당신이 방장입니다.\n", Color.BLUE );
            frame.setButtonToStart();
            sender.send( p );
            break;
            
        case RoomProtocol.ENTER_ROOM:
            System.out.println( p.getData() );
            frame.addUser( (String)p.getData() );
            break;
        }
    }
    
    private void handleProtocol( GameProtocol p ) {
        //System.out.println( "get Game Protocol" );
        // 클라이언트 동작
        // 게임 중 필요 한 프로토콜 동작
    	 GUIRunner run = frame.getRoomPanel().getRun();
    	 BoardState board = run.getBoard();
    	 
    	switch( p.getProtocol() ) {
    	 case GameProtocol.GAME_START:
    		run.setVisible();
    		break;
    	 case GameProtocol.GAME_READY:
    		 break;
    	 case GameProtocol.GAME_MOVE:
    		run.getGui().afterActionPerformed((int)p.getData());
    		 break;
    	 case GameProtocol.GAME_GIVE_UP:
    		 break;
    	 case GameProtocol.GAME_WIN:
    		 frame.messagePopup( null, "승리하였습니다!" );
    		 break;
    	 case GameProtocol.GAME_LOSE:
    		 frame.messagePopup( null, "패배하였습니다!" );
    		 break;
    	 case GameProtocol.GAME_QUIT:
    		 board.resetBoardState();
    		 run.getGui().updateBoard(board);
    		 run.getGui().setEnabled(false);
    		 
    		 break;
    	 case GameProtocol.GAME_CONFIRM:
    		 boolean yesNo = frame.confirmPopup("무르기", "상대방이 무르기를 요청하였습니다.\n승낙하시겠습니까?\n");
    			 sender.send(new GameProtocol(GameProtocol.GAME_CONFIRM, yesNo));
    		 
    		 break;
    	 case GameProtocol.GAME_UNDO:
    		 	run.undoProcess();
    		 break;
    	}
        
    }
}
