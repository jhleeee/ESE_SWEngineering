package common;

import java.io.Serializable;

public class UserInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String win;
    private String lose;
    private String rate;
    
    public UserInfo( String win, String lose ) {
        this.win = win;
        this.lose = lose;
        if(Integer.parseInt(lose) == 0)
        {
        	rate = "100";
        }
        else
        {
        	rate = Double.toString((Integer.parseInt(win)/Integer.parseInt(lose))*100);
        }
    }
    
    public String getWin() {
        return win;
    }
    public String getLose() {
        return lose;
    }
    public String getRate() {
        return rate;
    }
}