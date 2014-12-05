package client.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class GuiUtil
{
    public static void appendToPane( JTextPane tp, String msg, Color c )
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute( SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c );

        int len = tp.getDocument().getLength(); // same value as
                           // getText().length();
        tp.setCaretPosition( len ); // place caret at the end (with no selection)
        tp.setCharacterAttributes( aset, false );
        tp.replaceSelection( msg ); // there is no selection, so inserts at caret
    }
    
    public static JButton createFlatButton( String text ) {
        JButton button = new JButton( text );
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque( true );
        return button;
    }
    
    private static String createRoomNumberText( int idx ) {
        return "<HTML><font size=+1><b>"+idx+"</b></font> ";
    }
    public static String createButtonText( int idx ) {
        String text = createRoomNumberText( idx ) + "ºó ¹æ";
        return text;
    }
    
    public static String createButtonText( int idx, String name ) {
        String text = createRoomNumberText( idx ) + name;
        return text;
    }
}
