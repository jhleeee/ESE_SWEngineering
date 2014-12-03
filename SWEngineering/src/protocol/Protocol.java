package protocol;

import java.io.Serializable;

public interface Protocol extends Serializable
{
    public int getProtocol();
    void setProtocol(int protocol);
}
