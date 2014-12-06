package client.gui;

import java.awt.Color;
import java.awt.Dimension;
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

import client.gui.chess.*;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

public class RoomPanel extends JPanel implements PanelInterface
{
    private static final long serialVersionUID = 1L;
    
    private JTextField msg_textField;
    private JTextPane msg_textPane;
    private JTextField whisper_textField;
    private JButton exit_button;
    private JButton invitation_button;
    private JButton timeconfig_button;
    private JButton start_ready_button;
    private JButton undo_button;
    private JButton giveup_button;
    
    @Override
    public void printMessage(String msg, Color color) {
        msg_textPane.setEditable(true);
        GuiUtil.appendToPane( msg_textPane, msg, color );
        msg_textPane.setEditable(false);
    }

    @Override
    public void clear() {
        msg_textPane.setText("");
        //
        //
        //
        //
        //
        //
        //
        //
        //
        // 히스토리 클리어
        // 보드 클리어
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
    }
    
    void setButtonToReady() {
        start_ready_button.setText( "준비하기" );
    }
    
    void setButtonToStart() {
        start_ready_button.setText( "시작하기" );
    }
    
    /**
     * Create the panel.
     */
    public RoomPanel( final Sender sender, final MainFrame frame ) {
        setBackground(new Color(153, 204, 204));
        setLayout(null);
        JPanel chat_panel = new JPanel();
        chat_panel.setBounds(673, 231, 314, 459);
        chat_panel.setBackground(new Color(153, 204, 204));
        add(chat_panel);
        chat_panel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 0, 314, 428);
        chat_panel.add(scrollPane);
        
        msg_textPane = new JTextPane();
        msg_textPane.setEditable(false);
        //msg_textPane.setLineWrap( true );
        scrollPane.setViewportView( msg_textPane );
        
        msg_textField = new JTextField();
        msg_textField.setBounds(94, 438, 220, 21);
        chat_panel.add(msg_textField);
        msg_textField.setColumns(10);
        msg_textField.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String whisper_id = whisper_textField.getText();
                // 귓말이 아니면
                if( whisper_id.isEmpty() ) {
                    sender.send( new ChatProtocol( ChatProtocol.MESSAGE, msg_textField.getText() ) );    
                }
                // 귓말이면
                else {
                    sender.send( new ChatProtocol( ChatProtocol.WHISPER, msg_textField.getText(), whisper_id ) );
                }
                msg_textField.setText( "" );
            }
        });
        
        whisper_textField = new JTextField();
        whisper_textField.setBounds(0, 438, 82, 21);
        chat_panel.add(whisper_textField);
        whisper_textField.setColumns(10);
        
        exit_button = GuiUtil.createFlatButton("\uB098\uAC00\uAE30");
        exit_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setPanel( PanelInterface.LobbyPanel );
                sender.send( new RoomProtocol( RoomProtocol.EXIT_ROOM ) );
            }
        });
        exit_button.setBounds(891, 181, 97, 40);
        add(exit_button);
        
        invitation_button = GuiUtil.createFlatButton("\uCD08\uB300\uD558\uAE30");
        invitation_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sender.send( new RoomProtocol( RoomProtocol.REQUEST_INVITATION_USER_LIST ) );
            }
        });
        invitation_button.setBounds(782, 181, 97, 40);
        add(invitation_button);
        
        timeconfig_button = GuiUtil.createFlatButton("\uC2DC\uAC04\uC124\uC815");
        timeconfig_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        timeconfig_button.setBounds(673, 181, 97, 40);
        add(timeconfig_button);
        
        start_ready_button = GuiUtil.createFlatButton("시작하기");
        start_ready_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("겜시작????");
            	//run.setVisible();
            }
        });
        start_ready_button.setBounds(673, 131, 97, 40);
        add(start_ready_button);
        
        undo_button = GuiUtil.createFlatButton("무르기");
        undo_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        undo_button.setBounds(782, 131, 97, 40);
        add(undo_button);
        
        giveup_button = GuiUtil.createFlatButton("기권하기");
        giveup_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        giveup_button.setBounds(891, 131, 97, 40);
        add(giveup_button);
        
        JPanel history_panel = new JPanel();
        history_panel.setBackground(Color.WHITE);
        history_panel.setBounds(673, 10, 315, 110);
        add(history_panel);
        
        history_panel.setLayout(null);
        
        JLabel lblVs = new JLabel("VS");
        lblVs.setFont(new Font("굴림", Font.BOLD, 15));
        lblVs.setHorizontalAlignment(SwingConstants.CENTER);
        lblVs.setBounds(123, 42, 57, 25);
        history_panel.add(lblVs);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 14));
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel.setBounds(12, 17, 291, 25);
        history_panel.add(lblNewLabel);
        
        JLabel label = new JLabel("New label");
        label.setFont(new Font("굴림", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(12, 67, 291, 25);
        history_panel.add(label);
        
        
        JPanel board_panel = new JPanel();
        board_panel.setBounds(12, 10, 649, 680);
        add(board_panel);
        board_panel.setLayout(null);
        
        GUIRunner run = new GUIRunner(board_panel);
        
		//run.setVisible();
		
    }

}
