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
    // ���� �ʿ��ϸ� ����
    
    ClientReceiver( Socket socket ) throws IOException {
        sender = new Sender( socket );
        in = new ObjectInputStream( socket.getInputStream() );
        //
        // �׽�Ʈ
        Scanner scn = new Scanner( System.in );
        id = scn.next();
        sender.send( new ChatProtocol( 0, id ) );
        
        //
        //
        frame = new MainFrame( sender );
        frame.setVisible( true );
        
        // �׽�Ʈ
        sender.send( new ChatProtocol( 0, id ) );
    }
    
    Sender getSender() {
        return sender;
    }
    private void close() {
        this.interrupt();
        frame.close();
    }
    
    // �ʿ��� �޼ҵ� ������ �����
    
    
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
            // ���� ����
            frame.messagePopup( null, "������ ����Ǿ����ϴ�" );
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
            // �̸��� null �� ��� �� ����
            if( p.getName() == null ) {
                String name = frame.inputPopup( " ", "�� �̸��� �Է����ּ���" );
                if( name != null ) {
                    // �� �̸��� �Է��ߴٸ� ������ ����
                    p.setName( name );
                    sender.send( p );
                }
            }
            // �̸��� ������ ���� ���� �� ��
            else {
                // �� �гη� �ٲٰ�
                frame.setPanel( PanelInterface.RoomPanel );
                frame.setButtonToStart();
                //frame.setRoomTitle();
                sender.send( new RoomProtocol( RoomProtocol.ENTER_ROOM, id ) );
            }
            break;
            
        case LobbyProtocol.REJECT_CREATE_ROOM:
            frame.messagePopup( null, "���� ���� �� �� �����ϴ�" );
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
            frame.messagePopup( null, "�濡 ���� �� �� �����ϴ�" );
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
            // ���� �κ� ���� ���� ����ڵ��� ���̵� ���� ����Ʈ
            Vector<String> v = (Vector<String>)p.getData();
            // �ڱ� �ڽ��� �����ϰ� GUI�� �߰�
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
        // Ŭ���̾�Ʈ ����
        // �濡�� ���� ���� ������ ��, �غ�, �غ����, ���� ��
        switch( p.getProtocol() ) {
        case RoomProtocol.INVITATION_USER_LIST:
            frame.inviteList((Vector<String>) p.getData());
            break;
            
        case RoomProtocol.REJECT_INVITATION:
            frame.messagePopup( null, "�ش� ������ �ʴ��� �� �����ϴ�" );
            break;
            
        case RoomProtocol.REJECT_REQUEST_INVITATION_USER_LIST:
            frame.messagePopup( null, "�ʴ��� �� �����ϴ�" );
            break;
            
        case RoomProtocol.INVITE:
            if( frame.confirmPopup( null, p.getData() + "���� " + p.getNumber() + "�� �濡�� �ʴ��ϼ̽��ϴ�" ) ) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, p.getNumber() ));
            }
            else {
                
            }
            break;  
            
        case RoomProtocol.EXIT_ROOM:
            // �濡�� ��� ����
            frame.removeUser( (String)p.getData() );
            frame.printMessage( p.getData()+"���� �����̽��ϴ�.\n", Color.BLUE );
            break;
            
        case RoomProtocol.OWNER:
            frame.switchOwner();
            frame.printMessage( "����� �����Դϴ�.\n", Color.BLUE );
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
        // Ŭ���̾�Ʈ ����
        // ���� �� �ʿ� �� �������� ����
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
    		 frame.messagePopup( null, "�¸��Ͽ����ϴ�!" );
    		 break;
    	 case GameProtocol.GAME_LOSE:
    		 frame.messagePopup( null, "�й��Ͽ����ϴ�!" );
    		 break;
    	 case GameProtocol.GAME_QUIT:
    		 board.resetBoardState();
    		 run.getGui().updateBoard(board);
    		 run.getGui().setEnabled(false);
    		 
    		 break;
    	 case GameProtocol.GAME_CONFIRM:
    		 boolean yesNo = frame.confirmPopup("������", "������ �����⸦ ��û�Ͽ����ϴ�.\n�³��Ͻðڽ��ϱ�?\n");
    			 sender.send(new GameProtocol(GameProtocol.GAME_CONFIRM, yesNo));
    		 
    		 break;
    	 case GameProtocol.GAME_UNDO:
    		 	run.undoProcess();
    		 break;
    	}
        
    }
}
