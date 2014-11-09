package server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RoomManager
{
    private Map<String, Room> rooms;
    // flag 고려
    
    RoomManager() {
        rooms = Collections.synchronizedMap(
                new HashMap<String, Room>() );
    }
    
    // 둘 다 필요한 지 고려
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
