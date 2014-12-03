package protocol;

public class LobbyProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    public final static int ENTER_ROOM = 1000;
    public final static int CREATE_ROOM = 2000;
    public final static int EXIT_LOBBY = 3000;
    public final static int ENTER_LOBBY = 4000;
    public final static int USER_LIST = 5000;
    public final static int REJECT_ENTER_ROOM = 6000;
    public final static int REJECT_CREATE_ROOM = 7000;
    public final static int ADD_ROOM = 8000;
    public final static int DELETE_ROOM = 9000;
    public final static int ROOM_LIST = 10000;
    
    private int protocol = 0;
    private Object data = null;
    private String name = null;
    
    public LobbyProtocol( int protocol, Object data ) {
        this.protocol = protocol;
        this.data = data;
    }
    
    public void setData( Object data ) {
        this.data = data;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public Object getData() {
        return data;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void setProtocol( int protocol ) {
        this.protocol = protocol;
    }
    @Override
    public int getProtocol() {
        return protocol;
    }
}
