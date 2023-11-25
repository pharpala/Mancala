package mancala;

public class Store implements Countable{

    private int stoneCount=0;
    private Player owner;

    public Store() { //initializes a new store
        this.stoneCount = 0;
        this.owner = null;
    }

    public void addStones(int amount) { //add stones to the store
        this.stoneCount = stoneCount + amount;
    }  

    public Player getOwner() { //gets owner of the store
        return this.owner;
    }

    public void setOwner(Player player) { //sets the owner of the store
        this.owner = player;       
    }

    public int getStoneCount() {
        return this.stoneCount;
    }

    public void addStone() {
        this.stoneCount++;
    }

    public int removeStones() {
        int val = this.stoneCount;
        this.stoneCount = 0;
        return val;
    }