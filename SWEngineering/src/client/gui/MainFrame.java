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
import java.util.concurrent.Delayed;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.RoomInfo;
import common.Sender;
import protocol.ChatProtocol;
import protocol.LobbyProtocol;

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    static Point mouseDownCompCoords;
    
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
        setUndecorated(true);
        setResizable( false );
        setTitle( "Online Chess" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 400, 300, 0, 0 );
        contentPane = new JPanel();
        contentPane.setPreferredSize( new Dimension( 1000, 700 ) );
        contentPane.setLayout( new BorderLayout(0, 0) );
        setContentPane( contentPane );
        pack();
        
        addMouseListener(new MouseListener(){
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
            }
        });

        addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                System.exit(0);
            }
        });
        
        lobbyPanel = new LobbyPanel( sender );
        testRoomPanel = new TestRoomPanel( sender, this );
        //setPanel( PanelInterface.LobbyPanel );
        setPanel(PanelInterface.LobbyPanel);
    }

    public void clear() {
        testRoomPanel.clear();
        lobbyPanel.clear();
    }
    
    public void deleteRoom( int idx ) {
        lobbyPanel.deleteRoom( idx );
    }
    
    public void addRoom( String name, int idx ) {
        lobbyPanel.addRoom( name, idx );
    }
    
    public void setRoomStateToFull( int idx ) {
        lobbyPanel.setRoomStateToFull( idx );
    }

    public void setRoomStateToWaiting( int idx ) {
        lobbyPanel.setRoomStateToWaiting( idx );
    }

    public void setRoomStateToInGame( int idx ) {
        lobbyPanel.setRoomStateToInGame( idx );
    }
    
    public void addRoomCard() {
        lobbyPanel.addRoomCard();
    }
    
    public void setPanel( int type ) {
        clear();
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
        this.repaint();
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

    public void addRoomList( Vector<RoomInfo> vector ) {
        lobbyPanel.addRoomList( vector );
    }

    public void addUserList( Vector<String> vector ) {
        panel.addUserList( vector );
    }
    
    public void messagePopup( String title, String msg ) {
        JOptionPane.showMessageDialog( this, msg, title, JOptionPane.ERROR_MESSAGE );
    }
    
    public String inputPopup( String title, String msg ) {
        return JOptionPane.showInputDialog( this, msg, title, JOptionPane.QUESTION_MESSAGE );
    }
    
    public void close() {
        dispatchEvent( new WindowEvent( this, WindowEvent.WINDOW_CLOSING ));
    }
}
