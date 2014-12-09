package protocol;

public class GameProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    // 여기에 정의
    public static final int GAME_START = 2000;
    public static final int GAME_READY = 3000;
    public static final int GAME_MOVE = 4000;
    public static final int GAME_GIVE_UP = 5000;
    public static final int GAME_WIN = 6000;
    public static final int GAME_LOSE = 7000;
    public static final int GAME_QUIT = 8000;
    public static final int GAME_UNDO = 9000;
    public static final int GAME_CONFIRM = 1000;
    
    
    private Object data = null;
    private int protocol = 0;
    private boolean booleanValue = false;
    
    public GameProtocol( int protocol ) {
        this.protocol = protocol;
    }
    
    public GameProtocol( int protocol , Object data) {
        this.protocol = protocol;
        this.data = data;
    }
    
    public GameProtocol( int protocol , Object data, boolean booleanValue ) {
        this.protocol = protocol;
        this.data = data;
        this.booleanValue = booleanValue;
    }
    // set필요하면 만들어서 써
    public Object getData() {
        return data;
    }
    @Override
    public int getProtocol() {
        return protocol;
    }
	public void setData(String data) {
		this.data = data;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	public boolean isbooleanValue() {
		return booleanValue;
	}

	public void setOwner(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}
	
}
