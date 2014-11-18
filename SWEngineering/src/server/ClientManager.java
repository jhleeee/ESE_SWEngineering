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
