package client.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;

import protocol.LobbyProtocol;
import common.Sender;
import common.Util;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RoomCard extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private static int TotalCardNum = 0;
    private static ArrayList<JButton> roomButtonList = new ArrayList<JButton>();
    
    private int cardNum = 0;

    static void setButtonName( int idx, String name ) {
        JButton btn = roomButtonList.get( idx-1 );
        btn.setText( GuiUtil.createButtonText( idx, name ) );
    }
    
    static void setButtonToFull( int idx ) {
        JButton btn = roomButtonList.get( idx-1 );
        btn.setBackground( new Color(255, 153, 102) );
    }
    
    static void setButtonToWaiting( int idx ) {
        // 대기 중 화면으로 바꿔
        JButton btn = roomButtonList.get( idx-1 );
        btn.setBackground( new Color(159, 201, 60) ); 
    }
    
    static void setButtonToInGame( int idx ) {
        // 게임 중 화면
        JButton btn = roomButtonList.get( idx-1 );
        btn.setBackground( new Color(204, 102, 102) );
    }
    
    static void setButtonToEmpty( int idx ) {
        // 빈방으로
        JButton btn = roomButtonList.get( idx-1 );
        btn.setBackground( new Color(204, 204, 204) );
        btn.setText( GuiUtil.createButtonText( idx ) );
    }
    
    static ArrayList<JButton> getButtonList() {
        return roomButtonList;
    }
    
    static void clear() {
        int size = roomButtonList.size();
        for( int i=1; i<=size; ++i ) {
            setButtonToEmpty( i );
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
            JButton button = GuiUtil.createFlatButton( GuiUtil.createButtonText( (cardNum*12 + i ) ) );
            button.setBackground( new Color(204, 204, 204) );
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
}
