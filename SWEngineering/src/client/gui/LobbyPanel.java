package client.gui;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
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
import common.Sender;
import common.Util;

import java.awt.Font;
import java.util.Iterator;
import java.util.Vector;

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
    private int cardNum = 0;
    
    private Sender sender = null;
    
    public void addRoomCard() {
        roomList_panel.add( new RoomCard( sender ), Integer.toString( cardNum++ ) );
    }
    
    public void removeLastRoomCard() {
        Component[] components = roomList_panel.getComponents();
        cardLayout.removeLayoutComponent( components[ components.length ] );
        cardNum--;
    }
    
    @Override
    public void removeUser(String id) {
        userList.removeElement( id );
    }
    
    @Override
    public void addUser( String id ) {
        userList.addElement( id );
    }
    
    @Override
    public void printMessage( String msg, Color color ) {
        msg_textPane.setEditable(true);
        Util.appendToPane( msg_textPane, msg, color );
        msg_textPane.setEditable(false);
    }

    @Override
    public void addUserList( Vector<String> list ) {
        for( String id : list ) {
            addUser( id );
        }
    }
    
    /**
     * Create the panel.
     */
    public LobbyPanel( final Sender sender ) {
        this.sender = sender;
        setLayout(null);
        
        JPanel chat_panel = new JPanel();
        chat_panel.setBounds(250, 490, 737, 200);
        chat_panel.setBackground(Color.LIGHT_GRAY);
        add(chat_panel);
        chat_panel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(12, 10, 713, 149);
        chat_panel.add(scrollPane);
        
        msg_textPane = new JTextPane();
        msg_textPane.setEditable(false);
        //msg_textPane.setLineWrap( true );
        scrollPane.setViewportView( msg_textPane );
        
        msg_textField = new JTextField();
        msg_textField.setBounds(164, 169, 562, 21);
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
        whisper_textField.setBounds(12, 169, 140, 21);
        chat_panel.add(whisper_textField);
        whisper_textField.setColumns(10);
        
        JPanel userInfo_panel = new JPanel();
        userInfo_panel.setBounds(12, 551, 228, 139);
        userInfo_panel.setBackground(Color.LIGHT_GRAY);
        add(userInfo_panel);
        userInfo_panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("id");
        lblNewLabel.setFont(new Font("±º∏≤", Font.PLAIN, 13));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(84, 10, 57, 20);
        userInfo_panel.add(lblNewLabel);
        
        JLabel label = new JLabel("\uC2B9");
        label.setFont(new Font("±º∏≤", Font.BOLD, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(55, 40, 40, 20);
        userInfo_panel.add(label);
        
        JLabel label_1 = new JLabel("\uD328");
        label_1.setFont(new Font("±º∏≤", Font.BOLD, 12));
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(55, 70, 40, 20);
        userInfo_panel.add(label_1);
        
        JLabel label_2 = new JLabel("\uC2B9\uB960");
        label_2.setFont(new Font("±º∏≤", Font.BOLD, 12));
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(55, 100, 40, 20);
        userInfo_panel.add(label_2);
        
        JLabel win_label = new JLabel("0");
        win_label.setFont(new Font("±º∏≤", Font.BOLD, 12));
        win_label.setBackground(Color.WHITE);
        win_label.setHorizontalAlignment(SwingConstants.CENTER);
        win_label.setBounds(100, 40, 70, 20);
        userInfo_panel.add(win_label);
        
        JLabel lose_label = new JLabel("0");
        lose_label.setFont(new Font("±º∏≤", Font.BOLD, 12));
        lose_label.setHorizontalAlignment(SwingConstants.CENTER);
        lose_label.setBounds(100, 70, 70, 20);
        userInfo_panel.add(lose_label);
        
        JLabel rate_label = new JLabel("0");
        rate_label.setFont(new Font("±º∏≤", Font.BOLD, 12));
        rate_label.setHorizontalAlignment(SwingConstants.CENTER);
        rate_label.setBounds(100, 100, 70, 20);
        userInfo_panel.add(rate_label);
        
        roomList_panel = new JPanel();
        roomList_panel.setBackground(Color.LIGHT_GRAY);
        roomList_panel.setBounds(12, 10, 755, 470);
        add(roomList_panel);
        cardLayout = new CardLayout( 0,0 );
        roomList_panel.setLayout( cardLayout );
        
        
        JButton left_button = new JButton("<<");
        left_button.setBounds(12, 490, 109, 48);
        left_button.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                cardLayout.previous( roomList_panel );
            }
            
        } );
        add(left_button);
        
        JButton right_button = new JButton(">>");
        right_button.setBounds(133, 490, 105, 48);
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
        scrollPane_1.setViewportView(userList_list);
        userList_list.setCellRenderer(new DefaultListCellRenderer(){
            private static final long serialVersionUID = 1L;
            public int getHorizontalAlignment() {
                        return CENTER;
               }
        });
        userList_list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if( timer == null ) {
                    // 1√  ¥Î±‚ »ƒø° º±≈√ «ÿ¡¶
                    timer = new Timer( 1000, new ActionListener() {
                       public void actionPerformed(ActionEvent evt) {
                           userList_list.clearSelection();
                           // sender ∑Œ ¿Ø¿˙¡§∫∏ ø‰√ª
                       }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                else {
                    timer.restart();
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
