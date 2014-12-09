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

import common.RoomInfo;
import common.Sender;
import protocol.ChatProtocol;
import protocol.LobbyProtocol;

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private static Point mouseDownCompCoords;
    
    private Sender sender;
    private JPanel contentPane;
    
    private LobbyPanel lobbyPanel;
    private RoomPanel roomPanel;
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
                if( currCoords != null );
                    setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                System.exit(0);
            }
        });
        
        lobbyPanel = new LobbyPanel( sender );
        roomPanel = new RoomPanel( sender, this );
        setPanel( PanelInterface.LobbyPanel );
    }

    public void clear() {
        roomPanel.clear();
        lobbyPanel.clear();
    }
    
    public void switchOwner() {
        roomPanel.switchOwner();
    }
    
    public void deleteRoom( int idx ) {
        lobbyPanel.deleteRoom( idx );
    }
    
    public void setWinLabel( String val ) {
        lobbyPanel.setWinLabel( val );
    }
    public void setLoseLabel( String val ) {
        lobbyPanel.setLoseLabel( val );
    }
    public void setRateLabel( String val ) {
        lobbyPanel.setRateLabel( val );
    }
    
    public void setButtonToStart() {
        roomPanel.setButtonToStart();
    }
    
    public void setButtonToReady() {
        roomPanel.setButtonToReady();
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
            contentPane.add( roomPanel );
            roomPanel.setVisible( true );
            panel = ( PanelInterface )roomPanel;
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
        lobbyPanel.addUserList( vector );
    }
    
    public void messagePopup( String title, String msg ) {
        JOptionPane.showMessageDialog( this, msg, title, JOptionPane.ERROR_MESSAGE );
    }
    
    public String inputPopup( String title, String msg ) {
        return JOptionPane.showInputDialog( this, msg, title, JOptionPane.QUESTION_MESSAGE );
    }
    
    public boolean confirmPopup( String title, String msg ) {
        if (JOptionPane.showConfirmDialog( this, msg, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ) {
            return true;
        } else {
            return false;
        }
    }
    
    public void inviteList( Vector<String> list ) {
        new UserListFrame( getX()+400, getY()+100, list, sender );
    }
    
    public void close() {
        dispatchEvent( new WindowEvent( this, WindowEvent.WINDOW_CLOSING ));
    }
    
    public RoomPanel getRoomPanel() {
		return roomPanel;
	}
}
