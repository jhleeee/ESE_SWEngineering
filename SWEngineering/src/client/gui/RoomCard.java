package client.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;

import protocol.LobbyProtocol;
import common.Sender;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RoomCard extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private static int TotalCardNum = 0;
    private static ArrayList<JButton> roomButtonList = new ArrayList<JButton>();
    
    private int cardNum = 0;

    public static void setButtonToFull( JButton btn ) {
        btn.setBackground( new Color(255, 153, 102) );
    }
    
    public static void setButtonToWaiting( JButton btn ) {
        // 대기 중 화면으로 바꿔
        btn.setBackground( new Color(152, 153, 240) );
    }
    
    public static void setButtonToInGame( JButton btn ) {
        // 게임 중 화면
        btn.setBackground( new Color(204, 102, 102) );
    }
    
    public static void setButtonToEmpty( JButton btn ) {
        // 빈방으로
        btn.setBackground( new Color(204, 204, 204) );
    }
    
    public static ArrayList<JButton> getButtonList() {
        return roomButtonList;
    }
    
    public static JButton getRoomButton( int idx ) {
        return roomButtonList.get( idx );
    }
    

    public static void clear() {
        int idx = 1;
        for( JButton each : roomButtonList ) {
            each.setText( idx + " Empty" );
        }
    }
    
    /**
     * Create the panel.
     */
    public RoomCard( final Sender sender ) {
        cardNum = TotalCardNum++;
        setLayout(new GridLayout(4, 3, 10, 10));
        
        for( int i=1; i<=12; ++i ) {
            final int index = i;
            JButton button = createFlatButton( (cardNum*12 + i ) + " Empty" );
            button.addActionListener( new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent arg0 ) {
                    sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + index ) );
                }
            
            });
            add( button );
            roomButtonList.add( button );
        }
    }

    private JButton createFlatButton( String text ) {
        JButton button = new JButton( text );
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque( true );
        setButtonToEmpty( button );
        return button;
    }
}
