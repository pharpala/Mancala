package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private static final long serialVersionUID = 10;
    private String userName;
    private int kalahGamesPlayed;
    private int ayoGamesPlayed;
    private int kalahGamesWon;
    private int ayoGamesWon;

    private int gamesWon;
    private int gamesPlayed;

    //getters
    public String getName() {
        return userName;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    //incrementers
    public void addName(String user) {
        this.userName = user;
    }

    public void incrementKalahGamesPlayed() {
        kalahGamesPlayed++;
    }

    public void incrementAyoGamesPlayed() {
        ayoGamesPlayed++;
    }

    public void incrementGamesWon() {
        this.gamesWon++;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }
}
