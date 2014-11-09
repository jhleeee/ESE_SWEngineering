package protocol;

public class LobbyProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    public final static int ENTER_ROOM = 1000;
    public final static int CREATE_ROOM = 2000;
    public final static int EXIT_LOBBY = 3000;
    
    private int protocol = 0;
    private String data = null;
    
    public LobbyProtocol( int protocol, String data ) {
        this.protocol = protocol;
        this.data = data;
    }
    
    public void setData( String data ) {
        this.data = data;
    }
    public String getData() {
        return data;
    }
    public int getProtocol() {
        return protocol;
    }
}
