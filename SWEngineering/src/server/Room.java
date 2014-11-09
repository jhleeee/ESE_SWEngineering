package server;

import java.util.Iterator;

import common.Util;

import protocol.Protocol;

class Room implements ServerInterface
{
    private static int TotalRoomNumber = 1;
    
    private ClientManager userList;
    private String roomName;
    //private int roomNum;
    
    private boolean isInGame = false;
    
    Room( String roomName ) {
        this.roomName = roomName;
        userList = new ClientManager();
        //roomNum = TotalRoomNumber++;
        
        Util.println( "Room\t\tcreate new Room : " + roomName );
    }

    String getRoomName() {
        return roomName;
    }
    
    public void addUser( ServerReceiver r ) {
        Util.println( "Room\t\tadd user : "+roomName+"\t\t\t"+r.getInfo() );
        userList.addUser( r );
    }
    
    public ServerReceiver removeUser( String id ) {
        Util.println( "Room\t\tremove user : "+roomName+"\t\t\t"
                      + userList.getReceiver( id ).getInfo() );
        return userList.removeUser( id );
    }
    
    boolean isInGame() {
        return isInGame;
    }
    
    int getUserCount() {
        return userList.getSize();
    }
    
    @Override
    public void broadcast( Protocol data ) {
        Iterator<ServerReceiver> it = userList.getReceivers();
        while( it.hasNext() ) {
            it.next().getSender().send( data );
        }
    }

}
