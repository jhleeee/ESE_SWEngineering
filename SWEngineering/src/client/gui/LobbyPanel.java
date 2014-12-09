package client.gui;

import javax.swing.DefaultListCellRenderer;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.awt.Color;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

import java.awt.Panel;

import javax.swing.JTable;

import java.awt.CardLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import protocol.ChatProtocol;
import protocol.LobbyProtocol;
import common.RoomInfo;
import common.Sender;
import common.Util;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import server.Room;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class LobbyPanel extends JPanel implements PanelInterface
{
    private static final long serialVersionUID = 1L;
    
    private JTextField msg_textField;
    private JTextPane msg_textPane;
    private JTextField whisper_textField;
    
    private DefaultListModel<String> userList;
    private Timer timer = null;
    
    private JPanel roomList_panel;
    private CardLayout cardLayout;
    
    private Sender sender = null;
    private JLabel win_label;
    private JLabel lose_label;
    private JLabel rate_label;
    
    void setRoomStateToFull( int idx ) {
        RoomCard.setButtonToFull( idx );
    }

    void setRoomStateToWaiting( int idx ) {
        RoomCard.setButtonToWaiting( idx );
    }

    void setRoomStateToInGame( int idx ) {
        RoomCard.setButtonToInGame( idx );
    }
    
    void deleteRoom( int idx ) {
        RoomCard.setButtonToEmpty( idx );
    }

    
    void setWinLabel( String val ) {
        win_label.setText( val );;
    }
    void setLoseLabel( String val ) {
        lose_label.setText( val );;
    }
    void setRateLabel( String val ) {
        rate_label.setText( val );;
    }
    void addRoomCard() {
        RoomCard roomCard = new RoomCard( sender );
        roomCard.setBackground(new Color(153, 204, 204));
        roomList_panel.add( roomCard );
    }
    
    void removeLastRoomCard() {
        Component[] components = roomList_panel.getComponents();
        cardLayout.removeLayoutComponent( components[ components.length ] );
    }
    
    void addRoomList( Vector<RoomInfo> list ) {
        for( RoomInfo each : list ) {
            RoomCard.setButtonName( each.getRoomNumber(), each.getRoomName() );
            if( each.getUserNumber() == 2 ) {
                if( each.isInGame() ) {
                    RoomCard.setButtonToInGame( each.getRoomNumber() );
                }
                else {
                    RoomCard.setButtonToFull( each.getRoomNumber() );
                }
            }
            else {
                RoomCard.setButtonToWaiting( each.getRoomNumber() );
            }
        }
    }
    
    @Override
    public void removeUser(String id) {
        userList.removeElement( id );
    }
    
    @Override
    public void addUser( String id ) {
        userList.addElement( id );
    }
    
    public void addRoom( String name, int idx ) {
        RoomCard.setButtonName( idx, name );
        RoomCard.setButtonToWaiting( idx );
    }
    
    @Override
    public void printMessage( String msg, Color color ) {
        msg_textPane.setEditable(true);
        GuiUtil.appendToPane( msg_textPane, msg, color );
        msg_textPane.setEditable(false);
    }

    public void addUserList( Vector<String> list ) {
        for( String id : list ) {
            addUser( id );
        }
    }
    
    @Override
    public void clear() {
        userList.clear();
        RoomCard.clear();
    }
    
    /**
     * Create the panel.
     */
    public LobbyPanel( final Sender sender ) {
        setBackground(new Color(153, 204, 204));
        this.sender = sender;
        setLayout(null);
        
        JPanel chat_panel = new JPanel();
        chat_panel.setBounds(250, 490, 737, 200);
        chat_panel.setBackground(new Color(153, 204, 204));
        add(chat_panel);
        chat_panel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 0, 737, 169);
        chat_panel.add(scrollPane);
        
        msg_textPane = new JTextPane();
        msg_textPane.setEditable(false);
        //msg_textPane.setLineWrap( true );
        scrollPane.setViewportView( msg_textPane );
        
        msg_textField = new JTextField();
        msg_textField.setBounds(152, 179, 585, 21);
        chat_panel.add(msg_textField);
        msg_textField.setColumns(10);
        msg_textField.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String whisper_id = whisper_textField.getText();
                // ±”∏ª¿Ã æ∆¥œ∏È
                if( whisper_id.isEmpty() ) {
                    sender.send( new ChatProtocol( ChatProtocol.MESSAGE, msg_textField.getText() ) );    
                }
                // ±”∏ª¿Ã∏È
                else {
                    sender.send( new ChatProtocol( ChatProtocol.WHISPER, msg_textField.getText(), whisper_id ) );
                }
                msg_textField.setText( "" );
            }
        });
        
        whisper_textField = new JTextField();
        whisper_textField.setBounds(0, 179, 140, 21);
        chat_panel.add(whisper_textField);
        whisper_textField.setColumns(10);
        
        JPanel userInfo_panel = new JPanel();
        userInfo_panel.setBorder(null);
        userInfo_panel.setBounds(12, 575, 226, 115);
        userInfo_panel.setBackground(new Color(255, 255, 255));
        add(userInfo_panel);
        userInfo_panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("id");
        lblNewLabel.setFont(new Font("µ∏øÚ", Font.BOLD, 16));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(12, 10, 204, 20);
        userInfo_panel.add(lblNewLabel);
        
        JLabel label = new JLabel("\uC2B9");
        label.setFont(new Font("±º∏≤", Font.BOLD, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(55, 40, 40, 20);
        userInfo_panel.add(label);
        
        JLabel label_1 = new JLabel("\uD328");
        label_1.setFont(new Font("±º∏≤", Font.BOLD, 12));
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(55, 64, 40, 20);
        userInfo_panel.add(label_1);
        
        JLabel label_2 = new JLabel("\uC2B9\uB960");
        label_2.setFont(new Font("±º∏≤", Font.BOLD, 12));
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(55, 89, 40, 20);
        userInfo_panel.add(label_2);
        
        JLabel win_label = new JLabel("0");
        win_label.setFont(new Font("±º∏≤", Font.BOLD, 12));
        win_label.setBackground(Color.WHITE);
        win_label.setHorizontalAlignment(SwingConstants.CENTER);
        win_label.setBounds(107, 40, 70, 20);
        userInfo_panel.add(win_label);
        
        JLabel lose_label = new JLabel("0");
        lose_label.setFont(new Font("±º∏≤", Font.BOLD, 12));
        lose_label.setHorizontalAlignment(SwingConstants.CENTER);
        lose_label.setBounds(107, 64, 70, 20);
        userInfo_panel.add(lose_label);
        
        JLabel rate_label = new JLabel("0");
        rate_label.setFont(new Font("±º∏≤", Font.BOLD, 12));
        rate_label.setHorizontalAlignment(SwingConstants.CENTER);
        rate_label.setBounds(107, 89, 70, 20);
        userInfo_panel.add(rate_label);
        
        roomList_panel = new JPanel();
        roomList_panel.setBackground(Color.LIGHT_GRAY);
        roomList_panel.setBounds(12, 10, 755, 470);
        add(roomList_panel);
        cardLayout = new CardLayout( 0,0 );
        roomList_panel.setLayout( cardLayout );
        
        
        JButton left_button = new JButton("\u25C0");
        left_button.setForeground(Color.DARK_GRAY);
        left_button.setFont(new Font("±º∏≤", Font.BOLD, 20));
        left_button.setBounds(12, 490, 109, 33);
        left_button.setContentAreaFilled(false);
        left_button.setOpaque( true );
        left_button.setBackground(Color.LIGHT_GRAY);
        left_button.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                cardLayout.previous( roomList_panel );
            }
            
        } );
        add(left_button);
        
        JButton right_button = new JButton("\u25B6");
        right_button.setFont(new Font("±º∏≤", Font.BOLD, 20));
        right_button.setForeground(Color.DARK_GRAY);
        right_button.setBounds(133, 490, 105, 33);
        right_button.setContentAreaFilled(false);
        right_button.setOpaque( true );
        right_button.setBackground(Color.LIGHT_GRAY);
        right_button.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                cardLayout.next( roomList_panel );
            }
            
        } );
        add(right_button);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(779, 10, 209, 470);
        add(scrollPane_1);
        
        userList = new DefaultListModel<String>();
        final JList<String> userList_list = new JList<String>(userList);
        userList_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane_1.setViewportView(userList_list);
        userList_list.setCellRenderer(new DefaultListCellRenderer(){
            private static final long serialVersionUID = 1L;
            public int getHorizontalAlignment() {
                        return CENTER;
               }
        });
        
        JButton btnNewButton = new JButton("\uB098\uAC00\uAE30");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sender.send( new ChatProtocol( ChatProtocol.QUIT, (String)null ));
            }
        });
        btnNewButton.setForeground(Color.DARK_GRAY);
        btnNewButton.setFont(new Font("±º∏≤", Font.BOLD, 12));
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setOpaque( true );
        btnNewButton.setBackground(Color.LIGHT_GRAY);
        btnNewButton.setBounds(12, 533, 226, 33);
        add(btnNewButton);
        userList_list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if( timer == null ) {
                    // 1√  ¥Î±‚ »ƒø° º±≈√ «ÿ¡¶
                    timer = new Timer( 1000, new ActionListener() {
                       public void actionPerformed(ActionEvent evt) {
                           userList_list.clearSelection();
                           // sender ∑Œ ¿Ø¿˙¡§∫∏ ø‰√ª
                           sender.send( new LobbyProtocol( LobbyProtocol.REQUEST_USER_INFO, userList_list.getSelectedValue() ) );
                           
                       }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                else {
                    timer.restart();
                    // ≥ª¡§∫∏
                }
            }
        });
        
        addRoomCard(); // 1
        addRoomCard(); // 2
        addRoomCard(); // 3
        addRoomCard(); // 4
        addRoomCard(); // 5
    }
}
