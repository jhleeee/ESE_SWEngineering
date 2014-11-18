package client;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import client.gui.LobbyPanel;
import client.gui.MainFrame;
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
        frame.setPanel( new LobbyPanel( sender ) );
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
                // test
                else if( p instanceof TestProtocol )
                    handleProtocol( (TestProtocol)p );
            }
        }
        catch( SocketException e ) {
            // 서버 종료
            frame.popup( null, "서버가 종료되었습니다", JOptionPane.ERROR_MESSAGE );
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
            frame.printMessage( p.getData(), Color.BLACK );
            break;
            
        case ChatProtocol.WHISPER:
            frame.printMessage( p.getData(), Color.BLUE );
            break;
            
        case ChatProtocol.REJECT:
            frame.printMessage( p.getData(), Color.RED );
            break;
            
        case ChatProtocol.QUIT:
            
            
            break;
        }
    }
    
    private void handleProtocol( LobbyProtocol p ) {
        //System.out.println( "get Lobby Protocol" );
        switch( p.getProtocol() ) {
        case LobbyProtocol.ENTER_LOBBY:
            frame.addUser( p.getData() );
            break;
            
        case LobbyProtocol.USER_LIST:
            p.getList().remove( id );
            frame.addUserList( p.getList() );
            break;
            
        case LobbyProtocol.EXIT_LOBBY:
            frame.removeUser( p.getData() );
            break;
        }
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
