package common;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import protocol.*;

public class Sender
{
    private ObjectOutputStream out = null;
    
    public Sender( Socket socket ) {
        try {
            this.out = new ObjectOutputStream( socket.getOutputStream() );
        } 
        catch( IOException e ) {
            e.printStackTrace();
        }
    }
    public void send( Protocol data ) {
        if( out != null ) {
            try { 
                out.writeObject( data );
            } 
            catch( IOException e ) { 
            } 
        } 
    } 
}