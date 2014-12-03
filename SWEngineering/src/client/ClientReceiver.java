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
                // test
                else if( p instanceof TestProtocol )
                    handleProtocol( (TestProtocol)p );
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
            
            
            break;
        }
    }
    
    private void handleProtocol( LobbyProtocol p ) {
        switch( p.getProtocol() ) {
        case LobbyProtocol.ENTER_LOBBY:
            // 유저 아이디 추가
            frame.addUser( (String)p.getData() );
            break;
            
        case LobbyProtocol.CREATE_ROOM:
            // 이름이 null 인 경우 방 생성
            if( p.getName() == null ) {
                String name = frame.inputPopup( null, "방 이름을 입력해주세요" );
                if( name != null ) {
                    // 방 이름을 입력했다면 서버로 전송
                    p.setName( name );
                    sender.send( p );
                }
            }
            // 이름이 있으면 방이 생성 된 것
            else {
                // 방 패널로 바꾸고
                frame.setPanel( PanelInterface.TestRoomPanel );
                //frame.setRoomTitle();
                // data를 null로 바꾸어 전송하여 패널이 변경되었음을 알림
                // 서버는 이 프로토콜을 받고 방에 대한 정보를 전송함
                // 패널이 변경된 후 방 정보를 얻어와야 데이터가 정상적으로 처리 됨
                p.setData( null );
                sender.send( p );
            }
            break;
            
        case LobbyProtocol.REJECT_CREATE_ROOM:
            frame.messagePopup( null, "방을 생성 할 수 없습니다" );
            break;
            
        case LobbyProtocol.ENTER_ROOM:
            frame.setPanel( PanelInterface.TestRoomPanel );
            //frame.setRoomTitle();
            p.setData( null );
            sender.send( p );
            break;
            
        case LobbyProtocol.REJECT_ENTER_ROOM:
            frame.messagePopup( null, "방에 입장 할 수 없습니다" );
            break;
            
        case LobbyProtocol.ADD_ROOM:
            // 새로운 방이 생성되었으므로 프레임에 추가
            // 인덱스는 -1
            frame.addRoom( p.getName(), (Integer)p.getData()-1 );
            break;
            
        case LobbyProtocol.USER_LIST:
            // 현재 로비에 접속 중인 사용자들의 아이디를 담은 리스트
            @SuppressWarnings("unchecked")
            Vector<String> v = (Vector<String>) p.getData();
            // 자기 자신은 제외하고 GUI에 추가
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
