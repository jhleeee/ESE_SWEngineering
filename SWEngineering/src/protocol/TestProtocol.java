package protocol;

public class TestProtocol implements Protocol
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String data = null;
    private int protocol = 0;
    
    public TestProtocol( String i ) {
        data = i;
    }
    public String getData() {
        return data;
    }
}
