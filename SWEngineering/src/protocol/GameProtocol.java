package protocol;

public class GameProtocol implements Protocol
{
    private static final long serialVersionUID = 1L;
    
    // ���⿡ ����
    public static final int PROTOCOL = 1000;
    
    
    private String data = null;
    private int protocol = 0;
    
    // set�ʿ��ϸ� ���� ��
    public String getData() {
        return data;
    }
    public int getProtocol() {
        return protocol;
    }

}
