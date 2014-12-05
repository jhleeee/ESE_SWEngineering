package common;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Util
{
    public static void socketClose( Socket socket ) {
        if( socket != null ) {
            try { 
               socket.close(); 
            } 
            catch( IOException e ) {} 
       } 
   } 

   public static void socketClose( ServerSocket serverSoc ) {
        if( serverSoc != null ) {
            try { 
               serverSoc.close(); 
            } 
            catch( IOException e ) {} 
       } 
   } 
   

   public static String getTime() {
       SimpleDateFormat sdf;
       GregorianCalendar time;
       
       sdf = new SimpleDateFormat("hh:mm:ss");
       time = new GregorianCalendar();
       return sdf.format( time.getTime() );
   }
   
   public static void println( String str ) {
       System.out.println( getTime()+"\t"+str );
   }
}