package client.gui;

import java.awt.BorderLayout;
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

public class TestRoomPanel extends JPanel implements PanelInterface
{
    private static final long serialVersionUID = 1L;
    
    private JTextField msg_textField;
    private JTextPane msg_textPane;
    private JTextField whisper_textField;
    private JButton button;
    public BoardState board_state = new BoardState();
    public GUIRunner runner = new GUIRunner();
    public GUI gui = new GUI(board_state,runner);
    public ChessBoard board;
    
    Dimension boardSize = new Dimension(640,640);
    
    /**
     * Create the panel.
     */
    public TestRoomPanel( final Sender sender, final MainFrame frame ) {
    	
    	
    	
        setLayout(null);
        
        board = new ChessBoard(board_state, gui, boardSize);
		updateBoard(board_state);	
		board.setBounds(30, 30, 640, 640);
		board.setBackground(Color.BLUE);
		add(board);
        
        JPanel chat_panel = new JPanel();//채팅판넬
        chat_panel.setBounds(700, 490, 737, 200);
        chat_panel.setBackground(Color.LIGHT_GRAY);
        add(chat_panel);
        chat_panel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();//채팅스크롤 판넬
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(12, 10, 270, 149);
        chat_panel.add(scrollPane);
        
        msg_textPane = new JTextPane();//채팅텍스트만넬
        msg_textPane.setEditable(false);
        //msg_textPane.setLineWrap( true );
        scrollPane.setViewportView( msg_textPane );
        
        msg_textField = new JTextField();//메세지입력 판넬
        msg_textField.setBounds(164, 169, 562, 21);
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
        
        whisper_textField = new JTextField();//귓속말 판넬
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
        button.setBounds(700, 435, 97, 23);
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
    
    ////////////////////////////////////////체스
    public void updateBoard(BoardState boardState)
	{
		board.updateBoard(boardState);
	}

}
