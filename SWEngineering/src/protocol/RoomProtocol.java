package protocol;

import java.util.Vector;

public class RoomProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    public final static int READY = 1000;
    public final static int START = 2000;
    public final static int CANCLE = 3000; // 준비취소
    public final static int EXIT_ROOM = 4000;
    public final static int ENTER_ROOM = 5000;
    public final static int INVITE = 6000;
    public final static int REQUEST_INVITATION_USER_LIST = 7000;
    public final static int INVITATION_USER_LIST = 8000;
    public final static int USER_LIST = 9000;
    public final static int REJECT_INVITATION = 10000;
    public final static int REJECT_REQUEST_INVITATION_USER_LIST = 11000;
    public final static int OWNER = 12000;
    
    private int number = 0;
    private Object data = null;
    private int protocol = 0;
    
    public RoomProtocol( int protocol ) {
        this.protocol = protocol;
    }
    
    public RoomProtocol( int protocol, Object data ) {
        this.protocol = protocol;
        this.data = data;
    }
    
    public RoomProtocol( int protocol, Object data, int number ) {
        this.protocol = protocol;
        this.data = data;
        this.number = number;
    }
    
    public int getNumber() {
        return number;
    }
    
    public Object getData() {
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
