package ru.airhockey.waitingList;

import org.springframework.stereotype.Component;

/**
 * сопоставление пары игроков и gameId
 *
 * @author sibagatullin
 */
@Component
public class MatchedUser {
    private int user1;
    private int user2;
    private String gameID;

    public MatchedUser() {
    }

    public MatchedUser(int user1, int user2, String gameID) {
        this.user1 = user1;
        this.user2 = user2;
        this.gameID = gameID;
    }

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
