package mancala;

// Completed

public class Pit implements Countable{

    private int stoneCount;

    public Pit() {
        this.stoneCount = 0;
    }
    
    public void addStone() {
        this.stoneCount++;
    }

    public void addStones(int count) {
        this.stoneCount += count;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public int removeStones() {
        int stones = stoneCount;
        stoneCount = 0;
        return stones;
    }

    // Removed toString()
}

    




