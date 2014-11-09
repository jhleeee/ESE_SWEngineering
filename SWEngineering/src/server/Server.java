package server;

import java.net.ServerSocket;
import java.net.Socket;

import protocol.TestProtocol;
import common.*;

public class Server
{
    private static final int port = 7777;
    
    Server() {
        
        ServerSocket serverSocket = null; 
        Socket socket = null; 
        //LoginSession login = null;
        Lobby lobby = null;
        
        ServerReceiver r = null;
        Sender s1 = null;
        Sender s2 = null;
        int num = 0;
        try {
            serverSocket = new ServerSocket( port );
            Util.println( "Main\t\tstart main server" );
            // login = new LoginSession();
            lobby = new Lobby();
            while( true ) {
                socket = serverSocket.accept(); 
                Util.println( "Main\t\tconnected\t\t\t\t[" + socket.getInetAddress()
                        + ":" + socket.getPort() + "]" );
                //login.addUser( new ServerReceiver( socket ) );
                
                // 테스트
                r = new ServerReceiver( socket, lobby, Integer.toString( ++num ) );
                lobby.addUser( r );
                r.start();
                if( num == 1 ) {
                    //System.out.println( "send Test 1" );
                    s1 = r.getSender();
                    // 방 만들고
                    s1.send( new TestProtocol( "1" ) );
                }
                else if( num == 2 ) {
                    //System.out.println( "send Test 2" );
                    s2 = r.getSender();
                    // 방 접속
                    s2.send( new TestProtocol( "2" ) );
                }
            }
        }
        catch( Exception e ) {}
        finally {
            Util.socketClose( serverSocket );
        }
    }
    
    public static void main( String[] args ) {
        new Server();
    }
}
