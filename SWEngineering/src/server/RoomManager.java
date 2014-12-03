package server;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import common.RoomInfo;

public class RoomManager
{
    private Room[] rooms;
    private final int size;
    // flag °í·Á
    
    RoomManager( int size ) {
        rooms = new Room[50];
        this.size = size;
    }
    
    synchronized boolean addRoom( Room room, int idx ) {
        if( rooms[idx] != null )
            return false;
        else
            rooms[idx] = room;
        return true;
    }
    
    synchronized void deleteRoom( int idx ) {
        rooms[idx] = null;
    }
    
    ServerReceiver getReceiver( String id ) {
        ServerReceiver ret = null;
        for( Room each : rooms ) {
            if( each != null ) {
                ret = each.getReceiver( id );
                if( ret != null )
                    return ret;
            }
        }
        return null; 
    }
    
    /*
    ServerReceiver getReceiver( String id ) {
        Iterator<Room> it_room = rooms.values().iterator();
        while( it_room.hasNext() ) {
            ServerReceiver ret = it_room.next().getReceiver( id );
            if( ret != null )
                return ret;
            }
        return null;
    }
    */
    
    private RoomInfo toRoomInfo( Room room ) {
        return new RoomInfo( room.getRoomName(), room.getRoomNumber(), room.getSize(), room.isInGame() );
    }
    
    Vector<RoomInfo> getRoomList() {
        Vector<RoomInfo> vector = new Vector<RoomInfo>();
        for( Room each : rooms ) {
            if( each != null ) {
                vector.add( toRoomInfo( each ) );
            }
        }
        return vector;
    }
    
    Room getRoom( int idx ) {
        return rooms[idx];
    }
    
    
    int getSize() {
        return size;
    }
}
