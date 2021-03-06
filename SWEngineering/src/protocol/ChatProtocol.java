package protocol;

public class ChatProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    public static final int MESSAGE = 1000;
    public static final int WHISPER = 2000;
    public static final int SYSTEM = 3000;
    public static final int QUIT = 4000;
    public static final int NOTICE = 5000;

    private String who = null;
    private String data = null;
    private int protocol = 0;
    
    public ChatProtocol( int protocol, String data ) {
        this.protocol = protocol;
        this.data = data;
    }
    
    public ChatProtocol( int protocol, String data, String who ) {
        this.protocol = protocol;
        this.data = data;
        this.who = who;
    }
    
    public String getID() {
        return who;
    }
    public void setData( String data ) {
        this.data = data;
    }
    public String getData() {
        return data;
    }
    @Override
    public int getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(int protocol) {
        // TODO Auto-generated method stub
        
    }
}
