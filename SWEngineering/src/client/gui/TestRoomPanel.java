package client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import common.Sender;
import common.Util;
import protocol.ChatProtocol;
import protocol.RoomProtocol;

import javax.swing.JButton;

public class TestRoomPanel extends JPanel implements PanelInterface
{
    private static final long serialVersionUID = 1L;
    
    private JTextField msg_textField;
    private JTextPane msg_textPane;
    private JTextField whisper_textField;
    private JButton button;
    
    /**
     * Create the panel.
     */
    public TestRoomPanel( final Sender sender, final MainFrame frame ) {
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
        
        button = new JButton("\uB098\uAC00\uAE30");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setPanel( PanelInterface.LobbyPanel );
                sender.send( new RoomProtocol( RoomProtocol.EXIT_ROOM ) );
            }
        });
        button.setBounds(254, 435, 97, 23);
        add(button);
    }

    @Override
    public void printMessage(String msg, Color color) {
        msg_textPane.setEditable(true);
        GuiUtil.appendToPane( msg_textPane, msg, color );
        msg_textPane.setEditable(false);
    }

    @Override
    public void addUserList(Vector<String> vector) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addUser(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeUser(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clear() {
        msg_textPane.setText("");
    }

}
