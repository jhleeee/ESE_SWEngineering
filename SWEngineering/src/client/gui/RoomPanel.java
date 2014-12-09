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
import protocol.GameProtocol;
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
    
    private JLabel user1_label;
    private JLabel user2_label;
    private GUIRunner run;
    private int userNum = 0;
    public JPanel board_panel = null;
    @Override
    public void addUser(String id) {
        if( userNum == 0 ) {
            setUser1( id );
            userNum++;
        }
        else if( userNum == 1 ) {
            setUser2( id );
            userNum++;
        }
    }

    @Override
    public void removeUser(String id) {
        if( user1_label.getText().equals(id) ) {
            user1_label.setText("");
            userNum--;
        }
        else if( user2_label.getText().equals(id) ) {
            user2_label.setText("");
            userNum--;
        }
    }
    
    void switchOwner() {
        setUser1( user2_label.getText() );
        setUser2( "" );
    }
    
    private void setUser1( String id ) {
        user1_label.setText( id );
    }
    
    private void setUser2( String id ) {
        user2_label.setText( id );
    }
    
    @Override
    public void printMessage(String msg, Color color) {
        msg_textPane.setEditable(true);
        GuiUtil.appendToPane( msg_textPane, msg, color );
        msg_textPane.setEditable(false);
    }

    @Override
    public void clear() {
        userNum = 0;
        setUser1( "" );
        setUser2( "" );
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
    
    void startGame(GUIRunner run){
    	run.setVisible();
    }
    /**
     * Create the panel.
     */
    public RoomPanel(final Sender sender, final MainFrame frame ) {
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
            	 sender.send( new RoomProtocol( RoomProtocol.READY) );
            }
        });
        start_ready_button.setBounds(673, 131, 97, 40);
        add(start_ready_button);
        
        undo_button = GuiUtil.createFlatButton("무르기");
        undo_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sender.send( new GameProtocol( GameProtocol.GAME_UNDO) );
            }
        });
        undo_button.setBounds(782, 131, 97, 40);
        add(undo_button);
        
        giveup_button = GuiUtil.createFlatButton("기권하기");
        giveup_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sender.send( new GameProtocol( GameProtocol.GAME_GIVE_UP) );
            }
        });
        giveup_button.setBounds(891, 131, 97, 40);
        add(giveup_button);
        
        JPanel user_panel = new JPanel();
        user_panel.setBackground(Color.WHITE);
        user_panel.setBounds(673, 10, 315, 110);
        add(user_panel);
        
        user_panel.setLayout(null);
        
        JLabel lblVs = new JLabel("VS");
        lblVs.setFont(new Font("굴림", Font.BOLD, 15));
        lblVs.setHorizontalAlignment(SwingConstants.CENTER);
        lblVs.setBounds(123, 42, 57, 25);
        user_panel.add(lblVs);
        
        user1_label = new JLabel("");
        user1_label.setFont(new Font("굴림", Font.PLAIN, 14));
        user1_label.setHorizontalAlignment(SwingConstants.LEFT);
        user1_label.setBounds(12, 17, 291, 25);
        user_panel.add(user1_label);
        
        user2_label = new JLabel("");
        user2_label.setFont(new Font("굴림", Font.PLAIN, 14));
        user2_label.setHorizontalAlignment(SwingConstants.RIGHT);
        user2_label.setBounds(12, 67, 291, 25);
        user_panel.add(user2_label);
        
        board_panel = new JPanel();
        board_panel.setBounds(12, 10, 649, 680);
        add(board_panel);
        board_panel.setLayout(null);
        run = new GUIRunner(board_panel,sender);
        
		//run.setVisible();
        
    }

	public GUIRunner getRun() {
		return run;
	}

}
