package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import client.gui.LobbyPanel;
import client.gui.MainFrame;
import protocol.*;
import common.Sender;

class ClientReceiver extends Thread
{
    private Sender sender = null;
    private ObjectInputStream in = null;
    
    private int protocol = 0;
    private String data = null;
    
    MainFrame frame = null;
    // 변수 필요하면 생성
    
    
    ClientReceiver( Socket socket ) throws IOException {
        sender = new Sender( socket );
        in = new ObjectInputStream( socket.getInputStream() );
        
        frame = new MainFrame( sender );
        frame.setPanel( new LobbyPanel() );
        frame.setVisible( true );
    }
    
    Sender getSender() {
        return sender;
    }
    private void close() {
        this.interrupt();
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
                // test
                else if( p instanceof TestProtocol )
                    handleProtocol( (TestProtocol)p );
            }
        }
        catch( SocketException e ) {
            // 서버 종료
            close();
        }
        catch( IOException | ClassNotFoundException e ) {
            System.out.println("fail to read data");
        }

    }
    
    private void handleProtocol( ChatProtocol p ) {
        //System.out.println( "get Chat Protocol" );
        // 채팅, data가 보내는 메시지
        System.out.println( p.getData() );
    }
    
    private void handleProtocol( LobbyProtocol p ) {
        //System.out.println( "get Lobby Protocol" );
    }
    
    private void handleProtocol( RoomProtocol p ) {
        //System.out.println( "get Room Protocol" );
        // 클라이언트 동작
        // 방에서 게임 전에 가능한 것, 준비, 준비취소, 시작 등
        
        
    }
    
    private void handleProtocol( GameProtocol p ) {
        //System.out.println( "get Game Protocol" );
        // 클라이언트 동작
        // 게임 중 필요 한 프로토콜 동작
        
        
    }
    
    private void handleProtocol( TestProtocol p ) {
        //System.out.println( "get Test Protocol" );
        data = p.getData();
        if( data.equals( "1" ) ) {
            // 방 만들기 프로토콜 전송
            Protocol pc = new LobbyProtocol( LobbyProtocol.CREATE_ROOM, "test room" );
            sender.send( pc );
        }
        else if( data.equals( "2" ) ) {
            // 방 참가 프로토콜 전송
            Protocol pc = new LobbyProtocol( LobbyProtocol.ENTER_ROOM, "test room" );
            sender.send( pc );
        }
    }
}
