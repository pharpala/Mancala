package mancala;

import java.io.Serializable;

// completed

public class Store implements Countable, Serializable{

    private static final long serialVersionUID = 10;

    private Player owner;
    private int stoneCount;

    public Store() {
        stoneCount = 0;
        owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public int getStoneCount() {
        return stoneCount;
    }


    public void addStones(int amount) {
        stoneCount += amount;
    }

     public void addStone() {
        stoneCount ++;
    }

    public int removeStones() {
        int stones = stoneCount;
        stoneCount = 0;
        return stones;
    }

    // Removed toString()

}
   
