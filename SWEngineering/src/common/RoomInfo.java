package common;

import java.io.Serializable;

public class RoomInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String roomName;
    private int roomNum;
    private boolean isInGame;
    private int userNum;
    
    public RoomInfo( String roomName, int roomNum, int userNum, boolean isInGame ) {
        this.roomName = roomName;
        this.roomNum = roomNum;
        this.userNum = userNum;
        this.isInGame = isInGame;
    }

    public int getUserNumber() {
        return userNum;
    }
    
    public String getRoomName() {
        return roomName;
    }

    public int getRoomNumber() {
        return roomNum;
    }

    public boolean isInGame() {
        return isInGame;
    }
    
}