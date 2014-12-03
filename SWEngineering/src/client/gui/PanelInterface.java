package client.gui;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

public interface PanelInterface
{
    static final int LobbyPanel = 0;
    static final int RoomPanel = 1;
    static final int TestRoomPanel = 2;
    
    void printMessage( String msg, Color color );
    void addUserList( Vector<String> vector );
    void addUser( String id );
    void removeUser( String id );
}
