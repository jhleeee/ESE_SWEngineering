package server;

import java.util.Iterator;
import java.util.Vector;

import common.RoomInfo;
import common.Sender;
import common.Util;
import protocol.Protocol;


class Lobby implements ServerInterface
{
    private ClientManager userList;
    private RoomManager roomList;
    
    Lobby() {
        userList = new ClientManager();
        roomList = new RoomManager( 60 );
        
        Util.println( "Lobby\t\tcreate lobby" );
    }
    
    Room getRoom( int idx ) {
        return roomList.getRoom( idx );
    }
    /*
    Room getRoom( String name ) {
        return roomList.getRoom( name );
    }
    */
    boolean addRoom( Room room, int idx ) {
        return roomList.addRoom( room, idx );
    }
    
    public Sender getSender( String id ) {
        ServerReceiver sr = userList.getReceiver( id );
        if( sr != null ) {
            return sr.getSender();
        }
        else {
            sr = roomList.getReceiver( id );
            if( sr != null ) 
                return sr.getSender();
        }
        return null;
    }
    
    @Override
    public void addUser( ServerReceiver r ) {
        Util.println( "Lobby\t\tadd user\t\t\t\t"+r.getInfo() );
        userList.addUser( r );
    }
    
    @Override
    public ServerReceiver removeUser( String id ) {
        Util.println( "Lobby\t\tremove user\t\t\t\t"
                      +userList.getReceiver( id ).getInfo() );
        return userList.removeUser( id );
    }
    
    void deleteRoom( int idx ) {
        Util.println( "Lobby\t\tdelete room" + " : " + getRoom( idx ).getRoomName() );
        roomList.deleteRoom( idx );
    }
    
    @Override
    public void broadcast( Protocol data ) {
        Iterator<ServerReceiver> it = userList.getReceivers();
        while( it.hasNext() ) {
            it.next().getSender().send( data );
        }
    }

    public Vector<RoomInfo> getRoomList() {
        return roomList.getRoomList();
    }
    
    @Override
    public Vector<String> getUserList() {
        return userList.getIDs();
    }
}
