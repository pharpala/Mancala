package mancala;

public class Pit {

    private int stoneCount;

    public Pit() { //initializes a new pit
        this.stoneCount = 0;
    }

    public void addStone() { //adds a stone to the pit
        this.stoneCount++;
    }

    public int getStoneCount() { //gets the number of stones in the pit
        return this.stoneCount;
    }

    public int removeStones() { //removes and returns the numbers of stones from the pit
        int val = this.stoneCount;
        this.stoneCount = 0;
        return val;
    }
    
    @Override
    public String toString() {
        return "Pit with Stone Count: " + stoneCount;
    }

}
