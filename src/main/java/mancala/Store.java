package mancala;

public class Store {

    private int stoneCount=0;
    private Player owner;

    public Store() { //initializes a new store
        this.stoneCount = 0;
        this.owner = null;
    }

    public void addStones(int amount) { //add stones to the store
        this.stoneCount = stoneCount + amount;
    }

    public int emptyStore() { //empties a store and returns what was in there
        int val = this.stoneCount;
        this.stoneCount = 0;
        return val;
    }   

    public Player getOwner() { //gets owner of the store
        return this.owner;
    }

    public int getTotalStones() { //gets total number of stones in the store
        return this.stoneCount;
    }

    public void setOwner(Player player) { //sets the owner of the store
        this.owner = player;       
    }

@Override
public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }

    Store otherStore = (Store) obj;

    // Check if the names of the owners are both null or equal
return ((this.owner == null && otherStore.owner == null) 
|| (this.owner != null && this.owner.getName().equals(otherStore.owner.getName())));
}

@Override
    public String toString() {
        return "Store owned by: " 
               + (owner != null ? owner.getName() : "No owner") 
               +
               ", Total Stones: " 
               + stoneCount;
    }

}