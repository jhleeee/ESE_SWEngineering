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
import protocol.TestProtocol;
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
                sender.send( new ChatProtocol( ChatProtocol.REJECT, "[SYSTEM] �������� �ʾҰų� �������� �ʴ� ����Դϴ�.\n" ) );
            }
            else if( s.equals( sender ) ) {
                sender.send( new ChatProtocol( ChatProtocol.REJECT, "[SYSTEM] �ڱ� �ڽſ��� ���� �� �����ϴ�.\n" ) );
            }
            else {
                p.setData( id + " : " + p.getData() + "\n" );
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
            //server.broadcast( data );
            break;
        
        case LobbyProtocol.EXIT_LOBBY:
            lobby.removeUser( id );
            server.broadcast( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, id ) );
            break;
            
        case LobbyProtocol.CREATE_ROOM:
            userLocation = ServerInterface.IN_ROOM_OWNER;
            server = new Room( p.getData() );
            
            // �ߺ��˻� �� ��
            //
            //
            
            lobby.addRoom( (Room)server );
            server.addUser( lobby.removeUser( id ) );
            server.broadcast( new ChatProtocol( 
                              ChatProtocol.MESSAGE, id+"���� �����ϼ̽��ϴ�. " ));
            
            break;
            
        case LobbyProtocol.ENTER_ROOM:
            userLocation = ServerInterface.IN_ROOM_GUEST;
            
            // �������� Ȯ��
            //
            //
            
            server = lobby.getRoom( "test room" );
            server.addUser( lobby.removeUser( id ) );
            server.broadcast( new ChatProtocol( 
                              ChatProtocol.MESSAGE, id+"���� �����ϼ̽��ϴ�. " ));
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
