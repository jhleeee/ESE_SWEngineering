package client;

import java.io.IOException;
import java.net.Socket;

import client.gui.LobbyPanel;
import client.gui.MainFrame;
import common.Sender;
import common.Util;

public class Client
{
    private static final String serverIP = "192.168.0.8";
    private static final int port = 7777;
    
    private Socket socket = null;
    private Sender sender = null;
    //private String name = null;
    
    private ClientReceiver receiver = null;
    
    Client() {
        try {
            socket = new Socket( serverIP, port );
            receiver = new ClientReceiver( socket );
          
            /*
             * GUI 실행
             * gui 에 sender 를 넘겨주고, getSender() 로 얻음
             * sender.send( Protocol )을 통해 프로토콜 전송
             */
            
            receiver.start();
            receiver.join();
        } 
        catch( IOException e ) {
            System.out.println( "서버 종료" );
            System.exit(0);
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
        finally {
            Util.socketClose( socket );
        }
        
    }
    
    public static void main(String[] args) {
        new Client();
    }
}
