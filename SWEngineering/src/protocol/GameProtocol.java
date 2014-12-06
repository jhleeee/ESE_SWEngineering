package protocol;

public class GameProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    // 여기에 정의
    public static final int PROTOCOL = 1000;
    
    
    private String data = null;
    private int protocol = 0;
    
    // set필요하면 만들어서 써
    public String getData() {
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

}
