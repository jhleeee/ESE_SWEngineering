package client.gui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JPanel;

import common.Sender;
import common.Util;

import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.BorderLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Utilities;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

import java.awt.Color;

import javax.swing.JButton;

import protocol.ChatProtocol;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomPanel extends JPanel implements PanelInterface{

	private JPanel contentPane;
	private JTextPane msg_textPane;
	private JTextField msg_textField;
	private JTextField whisper_textField;
	/**
	 * Create the frame.
	 */
	public RoomPanel( final Sender sender ) 
	{
		setLayout(null);
		
		JPanel chat_panel = new JPanel();
        chat_panel.setBounds(529, 490, 458, 200);
        chat_panel.setBackground(new Color(204, 204, 204));
        add(chat_panel);
        chat_panel.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(12, 10, 50, 90);
        chat_panel.add(scrollPane);
        
        msg_textField = new JTextField();
        msg_textField.setBounds(74, 169, 372, 21);
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
        whisper_textField.setBounds(12, 169, 58, 21);
        chat_panel.add(whisper_textField);
        whisper_textField.setColumns(10);
        
        msg_textPane = new JTextPane();
        msg_textPane.setBounds(74, 10, 372, 147);
        chat_panel.add(msg_textPane);
        msg_textPane.setEditable(false);
	}

    @Override
    public void printMessage( String msg, Color color ) {
        msg_textPane.setEditable(true);
        Util.appendToPane( msg_textPane, msg, color );
        msg_textPane.setEditable(false);
    }

	@Override
	public void addUserList(Vector<String> vector) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUser(String id) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUser(String id) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() 
	{
		// TODO Auto-generated method stub
		// 보드 초기화
		//
		//
	}
}



