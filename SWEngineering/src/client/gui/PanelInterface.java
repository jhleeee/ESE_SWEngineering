package client.gui;

import java.util.Iterator;
import java.util.Vector;

public interface PanelInterface
{
    void printMessage( String msg );
    void addUserList( Vector<String> vector );
    void addUser( String id );
    void removeUser( String id );
}
