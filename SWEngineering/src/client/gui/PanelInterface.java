package client.gui;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

public interface PanelInterface
{
    static final int LobbyPanel = 0;
    static final int RoomPanel = 1;
    
    void addUser( String id );
    void removeUser( String id );
    void printMessage( String msg, Color color );
    void clear();
}
