package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import protocol.*;
import common.Sender;

class ClientReceiver extends Thread
{
    private Sender sender = null;
    private ObjectInputStream in = null;
    
    private int protocol = 0;
    private String data = null;
    // ���� �ʿ��ϸ� ����
    
    
    ClientReceiver( Socket socket ) throws IOException {
        sender = new Sender( socket );
        in = new ObjectInputStream( socket.getInputStream() );
    }
    
    Sender getSender() {
        return sender;
    }
    private void close() {
        this.interrupt();
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
            close();
        }
        catch( IOException | ClassNotFoundException e ) {
            System.out.println("fail to read data");
        }

    }
    
    private void handleProtocol( ChatProtocol p ) {
        //System.out.println( "get Chat Protocol" );
        // ä��, data�� ������ �޽���
        System.out.println( p.getData() );
    }
    
    private void handleProtocol( LobbyProtocol p ) {
        //System.out.println( "get Lobby Protocol" );
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