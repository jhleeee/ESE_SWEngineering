package server;

import java.util.Iterator;
import java.util.Vector;

import protocol.Protocol;

interface ServerInterface
{
    final static int LOBBY = 1;
    final static int IN_ROOM_OWNER = 2;
    final static int IN_ROOM_GUEST = 3;
    
    void broadcast( Protocol data );
    void addUser( ServerReceiver r );
    ServerReceiver removeUser( String id );
    Vector<String> getUserList();
}
