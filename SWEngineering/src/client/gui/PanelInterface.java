package client.gui;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

public interface PanelInterface
{
    void printMessage( String msg, Color color );
    void addUserList( Vector<String> vector );
    void addUser( String id );
    void removeUser( String id );
}
