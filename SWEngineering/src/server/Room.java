package server;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import common.Util;
import protocol.Protocol;

public class Room implements ServerInterface
{
    private static int TotalRoomNumber = 1;
    
    private ClientManager userList;
    private String roomName;
    private int roomNum;
    
    private boolean isInGame = false;
    
    Room( String roomName, int roomNum ) {
        this.roomName = roomName;
        this.roomNum = roomNum;
        userList = new ClientManager();
        //roomNum = TotalRoomNumber++;
        
        Util.println( "Room\t\tcreate new Room : " + roomName );
    }

    public String getRoomName() {
        return roomName;
    }
    
    public int getRoomNumber() {
        return roomNum;
    }
    
    public int getSize() {
        return userList.getSize();
    }
    
    @Override
    public void addUser( ServerReceiver r ) {
        Util.println( "Room\t\tadd user : "+roomName+"\t\t\t\t"+r.getInfo() );
        userList.addUser( r );
    }
    
    @Override
    public ServerReceiver removeUser( String id ) {
        Util.println( "Room\t\tremove user : "+roomName+"\t\t\t"
                      + userList.getReceiver( id ).getInfo() );
        return userList.removeUser( id );
    }
    
    ServerReceiver getReceiver( String id ) {
        return userList.getReceiver( id );
    }
    
    Iterator<ServerReceiver> getReceivers() {
        return userList.getReceivers();
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

    @Override
    public Vector<String> getUserList() {
        return userList.getIDs();
    }
}
