package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


class ClientManager
{
    private Map<String, ServerReceiver> clients;
    // get iterator flag 고려
    
    ClientManager() {
        clients = Collections.synchronizedMap(
                new HashMap<String, ServerReceiver>() );
    }
    /*
    ClientManager( String id, ServerReceiver r ) {
        clients = Collections.synchronizedMap(
                new HashMap<String, ServerReceiver>() );   
        
        addUser( id, r );
    }
    ClientManager( String id1, ServerReceiver r1,
                          String id2, ServerReceiver r2 ) {
        clients = Collections.synchronizedMap(
                new HashMap<String, ServerReceiver>() );       
        
        addUser( id1, r1 );
        addUser( id2, r2 );
    }
    */
    
    synchronized void addUser( ServerReceiver r ) {
        clients.put( r.getID(), r );
    }
    synchronized void addUser( String id, ServerReceiver r ) {
        clients.put( id, r );
    }
    // 리턴 값 필요 할 수도
    synchronized ServerReceiver removeUser( String id ) {
        return clients.remove( id );
    }
    
    ServerReceiver getReceiver( String id ) {
        return clients.get( id );
    }
    /*
    Vector<ServerReceiver> getReceivers() {
        Vector<ServerReceiver> ret = new Vector<ServerReceiver>(getSize());
        Iterator<ServerReceiver> it = clients.values().iterator();
        while( it.hasNext() ) {
            ret.add( it.next() );
        }
        return ret;
    }
    */
    Vector<String> getIDs() {
        Vector<String> ret = new Vector<String>(getSize());
        Iterator<String> it = clients.keySet().iterator();
        while( it.hasNext() ) {
            ret.add( it.next() );
        }
        return ret;
    }
    
    Iterator<ServerReceiver> getReceivers() {
        ArrayList ls;
        return clients.values().iterator();
    }
    /*
    Iterator<String> getIDs() {
        return clients.keySet().iterator();
    }*/
    
    int getSize() {
        return clients.size();
    }
}
