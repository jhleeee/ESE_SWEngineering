package protocol;

public class ChatProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    public static final int MESSAGE = 1000;
    
    private String data = null;
    private int protocol = 0;
    
    public ChatProtocol( int protocol, String data ) {
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
