package client.gui;

import java.awt.BorderLayout;
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

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Sender;
import protocol.ChatProtocol;

public class MainFrame extends JFrame implements PanelInterface
{
    //static Point mouseDownCompCoords;
    
    private Sender sender;
    private JPanel contentPane;
    
    private LobbyPanel lobbyPanel;
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
                sender.send( new ChatProtocol( ChatProtocol.QUIT, null ));
                System.exit(0);
            }
        });
    }

    
    public void setPanel( LobbyPanel lp ) {
        contentPane.removeAll();
        
        lobbyPanel = lp;
        contentPane.add( lp );
    }
    /*
    public void setPanel( RoomPanel rp ) {
        
    }
    */
    
    @Override
    public void printMessage( String msg ) {
        panel.printMessage( msg );
    }

    @Override
    public void printUserList() {
        panel.printUserList();
        
    }
}
