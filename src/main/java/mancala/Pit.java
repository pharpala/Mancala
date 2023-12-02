package mancala;

import java.io.Serializable;

// Completed

public class Pit implements Countable, Serializable{
    
    private static final long serialVersionUID = 10;
    private int stoneCount;

    public Pit() {
        this.stoneCount = 0;
    }
    
    @Override
    public void addStone() {
        this.stoneCount++;
    }

    @Override
    public void addStones(int count) {
        this.stoneCount += count;
    }

    @Override
    public int getStoneCount() {
        return stoneCount;
    }

    @Override
    public int removeStones() {
        int stones = stoneCount;
        stoneCount = 0;
        return stones;
    }

    // Removed toString()
}

    




