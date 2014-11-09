package server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RoomManager
{
    private Map<String, Room> rooms;
    // flag ���
    
    RoomManager() {
        rooms = Collections.synchronizedMap(
                new HashMap<String, Room>() );
    }
    
    // �� �� �ʿ��� �� ���
    synchronized void addRoom( Room room ) {
        rooms.put( room.getRoomName(), room );
    }
    synchronized void addRoom( String name, Room room ) {
        rooms.put( name, room );
    }
    
    synchronized void deleteRoom( String name ) {
        rooms.remove( name );
    }
    
    Room getRoom( String name ) {
        return rooms.get( name );
    }
    
    Iterator<String> getRoomNames() {
        return rooms.keySet().iterator();
    }
    
    int getSize() {
        return rooms.size();
    }
}
