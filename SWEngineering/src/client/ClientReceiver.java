package client;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;

import client.gui.LobbyPanel;
import client.gui.MainFrame;
import client.gui.PanelInterface;
import protocol.*;
import common.Sender;

class ClientReceiver extends Thread
{
    private Sender sender = null;
    private ObjectInputStream in = null;
    
    private String id = null;
    private int protocol = 0;
    private String data = null;
    
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
                // test
                else if( p instanceof TestProtocol )
                    handleProtocol( (TestProtocol)p );
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
            
            
            break;
        }
    }
    
    private void handleProtocol( LobbyProtocol p ) {
        switch( p.getProtocol() ) {
        case LobbyProtocol.ENTER_LOBBY:
            // ���� ���̵� �߰�
            frame.addUser( (String)p.getData() );
            break;
            
        case LobbyProtocol.CREATE_ROOM:
            // �̸��� null �� ��� �� ����
            if( p.getName() == null ) {
                String name = frame.inputPopup( null, "�� �̸��� �Է����ּ���" );
                if( name != null ) {
                    // �� �̸��� �Է��ߴٸ� ������ ����
                    p.setName( name );
                    sender.send( p );
                }
            }
            // �̸��� ������ ���� ���� �� ��
            else {
                // �� �гη� �ٲٰ�
                frame.setPanel( PanelInterface.TestRoomPanel );
                //frame.setRoomTitle();
                // data�� null�� �ٲپ� �����Ͽ� �г��� ����Ǿ����� �˸�
                // ������ �� ���������� �ް� �濡 ���� ������ ������
                // �г��� ����� �� �� ������ ���;� �����Ͱ� ���������� ó�� ��
                p.setData( null );
                sender.send( p );
            }
            break;
            
        case LobbyProtocol.REJECT_CREATE_ROOM:
            frame.messagePopup( null, "���� ���� �� �� �����ϴ�" );
            break;
            
        case LobbyProtocol.ENTER_ROOM:
            frame.setPanel( PanelInterface.TestRoomPanel );
            //frame.setRoomTitle();
            p.setData( null );
            sender.send( p );
            break;
            
        case LobbyProtocol.REJECT_ENTER_ROOM:
            frame.messagePopup( null, "�濡 ���� �� �� �����ϴ�" );
            break;
            
        case LobbyProtocol.ADD_ROOM:
            // ���ο� ���� �����Ǿ����Ƿ� �����ӿ� �߰�
            // �ε����� -1
            frame.addRoom( p.getName(), (Integer)p.getData()-1 );
            break;
            
        case LobbyProtocol.USER_LIST:
            // ���� �κ� ���� ���� ����ڵ��� ���̵� ���� ����Ʈ
            @SuppressWarnings("unchecked")
            Vector<String> v = (Vector<String>) p.getData();
            // �ڱ� �ڽ��� �����ϰ� GUI�� �߰�
            v.remove( id );
            frame.addUserList( v );
            break;
            
        case LobbyProtocol.EXIT_LOBBY:
            frame.removeUser( (String)p.getData() );
            break;
        }
    }
    
    private void handleProtocol( RoomProtocol p ) {
        //System.out.println( "get Room Protocol" );
        // Ŭ���̾�Ʈ ����
        // �濡�� ���� ���� ������ ��, �غ�, �غ����, ���� ��
        
        
    }
    
    private void handleProtocol( GameProtocol p ) {
        //System.out.println( "get Game Protocol" );
        // Ŭ���̾�Ʈ ����
        // ���� �� �ʿ� �� �������� ����
        
        
    }
    
    private void handleProtocol( TestProtocol p ) {
        //System.out.println( "get Test Protocol" );
        data = p.getData();
        if( data.equals( "1" ) ) {
            // �� ����� �������� ����
            Protocol pc = new LobbyProtocol( LobbyProtocol.CREATE_ROOM, "test room" );
            sender.send( pc );
        }
        else if( data.equals( "2" ) ) {
            // �� ���� �������� ����
            Protocol pc = new LobbyProtocol( LobbyProtocol.ENTER_ROOM, "test room" );
            sender.send( pc );
        }
    }
}
