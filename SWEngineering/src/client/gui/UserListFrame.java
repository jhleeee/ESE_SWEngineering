package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import protocol.RoomProtocol;
import common.Sender;

public class UserListFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private static Point mouseDownCompCoords;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public UserListFrame( int x, int y, Vector<String> userList, final Sender sender ) {
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(x, y, 236, 527);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(12,10,212,459);
        getContentPane().add(scrollPane_1);
        
        final JList<String> userList_list = new JList<String>(userList.toArray( new String[userList.size()]));
        userList_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)userList_list.getCellRenderer();  
        renderer.setHorizontalAlignment( JLabel.CENTER );  
        scrollPane_1.setViewportView(userList_list);
        
        JButton btnNewButton = GuiUtil.createFlatButton("초 대");
        btnNewButton.setBackground(Color.LIGHT_GRAY);
        btnNewButton.setBounds(14, 479, 97, 35);
        btnNewButton.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String id = userList_list.getSelectedValue();
                if( id != null ) {
                    sender.send( new RoomProtocol( RoomProtocol.INVITE, id ));
                    close();
                }
            }
            
        } );
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = GuiUtil.createFlatButton("취 소");
        btnNewButton_1.setBackground(Color.LIGHT_GRAY);
        btnNewButton_1.setBounds(123, 479, 97, 35);
        btnNewButton_1.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                close();
            }
            
        } );
        contentPane.add(btnNewButton_1);

        addMouseListener(new MouseListener(){
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
            }
        });

        addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                System.exit(0);
            }
        });
        setVisible( true );
    }
    
    private void close() {
        setVisible( false );
    }
}
