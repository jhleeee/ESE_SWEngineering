package client.gui;

import javax.swing.JPanel;

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

    public static JButton getRoomButton( int idx ) {
        return roomButtonList.get( idx );
    }
    
    /**
     * Create the panel.
     */
    public RoomCard( final Sender sender ) {
        cardNum = TotalCardNum++;
        setLayout(new GridLayout(4, 3, 10, 10));
        
        JButton room_btn_1 = new JButton( (cardNum*12 + 1) + " Empty" );
        room_btn_1.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 1 ) );
            }
        
        });
        add(room_btn_1);
        roomButtonList.add( room_btn_1 );
        
        JButton room_btn_2 = new JButton( (cardNum*12 + 2) + " Empty" );
        room_btn_2.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 2 ) );
            }
        
        });
        add(room_btn_2);
        roomButtonList.add( room_btn_2 );
        
        JButton room_btn_3 = new JButton( (cardNum*12 + 3) + " Empty" );
        room_btn_3.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 3 ) );
            }
        
        });
        add(room_btn_3);
        roomButtonList.add( room_btn_3 );
        
        JButton room_btn_4 = new JButton( (cardNum*12 + 4) + " Empty" );
        room_btn_4.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 4 ) );
            }
        
        });
        add(room_btn_4);
        roomButtonList.add( room_btn_4 );
        
        JButton room_btn_5 = new JButton( (cardNum*12 + 5) + " Empty" );
        room_btn_5.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 5 ) );
            }
        
        });
        add(room_btn_5);
        roomButtonList.add( room_btn_5 );
        
        JButton room_btn_6 = new JButton( (cardNum*12 + 6) + " Empty" );
        room_btn_6.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 6 ) );
            }
        
        });
        add(room_btn_6);
        roomButtonList.add( room_btn_6 );
        
        JButton room_btn_7 = new JButton( (cardNum*12 + 7) + " Empty" );
        room_btn_7.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 7 ) );
            }
        
        });
        add(room_btn_7);
        roomButtonList.add( room_btn_7 );
        
        JButton room_btn_8 = new JButton( (cardNum*12 + 8) + " Empty" );
        room_btn_8.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 8 ) );
            }
        
        });
        add(room_btn_8);
        roomButtonList.add( room_btn_8 );
        
        JButton room_btn_9 = new JButton( (cardNum*12 + 9) + " Empty" );
        room_btn_9.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 9 ) );
            }
        
        });
        add(room_btn_9);
        roomButtonList.add( room_btn_9 );
        
        JButton room_btn_10 = new JButton( (cardNum*12 + 10) + " Empty" );
        room_btn_10.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 10 ) );
            }
        
        });
        add(room_btn_10);
        roomButtonList.add( room_btn_10 );
        
        JButton room_btn_11 = new JButton( (cardNum*12 + 11) + " Empty" );
        room_btn_11.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 11 ) );
            }
        
        });
        add(room_btn_11);
        roomButtonList.add( room_btn_11 );
        
        JButton room_btn_12 = new JButton( (cardNum*12 + 12) + " Empty" );
        room_btn_12.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sender.send( new LobbyProtocol( LobbyProtocol.ENTER_ROOM, cardNum*12 + 12 ) );
            }
        
        });
        add(room_btn_12);
        roomButtonList.add( room_btn_12 );

    }

}
