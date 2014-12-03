package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.Sender;
import protocol.ChatProtocol;
import protocol.LobbyProtocol;

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    //static Point mouseDownCompCoords;
    
    private Sender sender;
    private JPanel contentPane;
    
    private LobbyPanel lobbyPanel;
    //private RoomPanel roomPanel;
    private TestRoomPanel testRoomPanel;
    private PanelInterface panel;

    /**
     * Create the frame.
     */
    public MainFrame( final Sender sender ) {
        this.sender = sender;
        //setUndecorated(true);
        setResizable( false );
        setTitle( "Online Chess" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 400, 300, 0, 0 );
        contentPane = new JPanel();
        contentPane.setPreferredSize( new Dimension( 1000, 700 ) );
        contentPane.setLayout( new BorderLayout(0, 0) );
        setContentPane( contentPane );
        pack();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                sender.send( new LobbyProtocol( LobbyProtocol.EXIT_LOBBY, (String)null ));
                System.exit(0);
            }
        });
        
        lobbyPanel = new LobbyPanel( sender );
        testRoomPanel = new TestRoomPanel( sender );
        setPanel( PanelInterface.LobbyPanel );
    }

    public void addRoom( String name, int idx ) {
        JButton btn = RoomCard.getRoomButton( idx );
        btn.setText( (idx+1) + " " + name );
    }
    
    public void setRoomState( int idx, int state ) {
        
    }
    
    public void addRoomCard() {
        lobbyPanel.addRoomCard();
    }
    
    public void setPanel( int type ) {
        contentPane.removeAll();
        switch( type ) {
        case PanelInterface.LobbyPanel:
            contentPane.add( lobbyPanel );
            lobbyPanel.setVisible( true );
            panel = ( PanelInterface )lobbyPanel;
            break;
            
        case PanelInterface.RoomPanel:
            break;
            
        case PanelInterface.TestRoomPanel:
            contentPane.add( testRoomPanel );
            testRoomPanel.setVisible( true );
            panel = ( PanelInterface )testRoomPanel;
            break;
        }
        this.pack();
    }
    
    public void removeUser( String id ) {
        panel.removeUser( id );
    }

    public void addUser( String id ) {
        panel.addUser( id );
    }
    

    public void printMessage( String msg, Color color ) {
        panel.printMessage( msg, color );
    }


    public void addUserList( Vector<String> vector ) {
        panel.addUserList( vector );
        
    }
    
    public void messagePopup( String title, String msg ) {
        JOptionPane.showMessageDialog( this, msg, title, JOptionPane.ERROR_MESSAGE );
    }
    public String inputPopup( String title, String msg ) {
        return JOptionPane.showInputDialog( this, msg, title );
    }
    
    public void close() {
        dispatchEvent( new WindowEvent( this, WindowEvent.WINDOW_CLOSING ));
    }
}
