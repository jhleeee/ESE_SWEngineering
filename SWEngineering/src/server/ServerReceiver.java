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
    
    ServerReceiver( Socket socket, Lobby lobby, String id ) throws IOException{
        sender = new Sender( socket );
        in = new ObjectInputStream( socket.getInputStream() );
        this.lobby = lobby;
        this.socket = socket;
        this.id = id;
        
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
        this.interrupt();
    }
    String getID() {
        return id;
    }
    
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
            Util.println( "ServerReceiver\tconnection closed\t\t\t"+getInfo() );
            close();
        }
        catch( IOException | ClassNotFoundException e ) {
            e.printStackTrace();
            System.out.println("fail to read data");
        }

    }
    
    private void handleProtocol( ChatProtocol data ) {
        //System.out.println( "get Chat Protocol" );
    }
    
    private void handleProtocol( LobbyProtocol data ) {
        //System.out.println( "get Lobby Protocol" );
        switch( data.getProtocol() ) {
        
        case LobbyProtocol.CREATE_ROOM:
            userLocation = ServerInterface.IN_ROOM_OWNER;
            server = new Room( data.getData() );
            
            // 중복검사 할 것
            //
            //
            
            lobby.addRoom( (Room)server );
            server.addUser( lobby.removeUser( id ) );
            server.broadcast( new ChatProtocol( 
                              ChatProtocol.MESSAGE, id+"님이 참가하셨습니다. " ));
            
            break;
            
        case LobbyProtocol.ENTER_ROOM:
            userLocation = ServerInterface.IN_ROOM_GUEST;
            
            // 참가가능 확인
            //
            //
            
            server = lobby.getRoom( "test room" );
            server.addUser( lobby.removeUser( id ) );
            server.broadcast( new ChatProtocol( 
                              ChatProtocol.MESSAGE, id+"님이 참가하셨습니다. " ));
            break;
        }
    }
    
    private void handleProtocol( RoomProtocol data ) {
        //System.out.println( "get Room Protocol" );
        switch( data.getProtocol() ) {
        
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
    
    private void handleProtocol( GameProtocol data ) {
        //System.out.println( "get Game Protocol" );
        switch( data.getProtocol() ) {
        
        case GameProtocol.PROTOCOL:
            // do something
        }
    }
    

}
