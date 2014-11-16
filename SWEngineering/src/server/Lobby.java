package server;

import java.util.Iterator;
import java.util.Vector;

import common.Util;
import protocol.Protocol;


class Lobby implements ServerInterface
{
    private ClientManager userList;
    private RoomManager roomList;
    
    Lobby() {
        userList = new ClientManager();
        roomList = new RoomManager();
        
        Util.println( "Lobby\t\tcreate lobby" );
    }
    
    Room getRoom( String name ) {
        return roomList.getRoom( name );
    }
    
    void addRoom( Room room ) {
        roomList.addRoom( room );
    }
    
    public void addUser( ServerReceiver r ) {
        Util.println( "Lobby\t\tadd user\t\t\t\t"+r.getInfo() );
        userList.addUser( r );
    }
    
    public ServerReceiver removeUser( String id ) {
        Util.println( "Lobby\t\tremove user\t\t\t\t"
                      +userList.getReceiver( id ).getInfo() );
        return userList.removeUser( id );
    }
    
    void deleteRoom( String name ) {
        Util.println( "Lobby\t\tdelete room" + " : " + name );
        roomList.deleteRoom( name );
    }
    
    @Override
    public void broadcast( Protocol data ) {
        Iterator<ServerReceiver> it = userList.getReceivers();
        while( it.hasNext() ) {
            it.next().getSender().send( data );
        }
    }

    @Override
    public Vector<String> getUserList() {
        return userList.getIDs();
    }
}
