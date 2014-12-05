package protocol;

public class RoomProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    public final static int READY = 1000;
    public final static int START = 2000;
    public final static int CANCLE = 3000; // 준비취소
    public final static int EXIT_ROOM = 4000;
    public final static int ENTER_ROOM = 5000;
    
    private String data = null;
    private int protocol = 0;
    
    public RoomProtocol( int protocol ) {
        this.protocol = protocol;
    }
    
    public RoomProtocol( int protocol, String data ) {
        
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
        this.protocol = protocol;  
    }
}
