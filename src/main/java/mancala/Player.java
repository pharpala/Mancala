package mancala;
import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private Store store;

    private static final long serialVersionUID = 10; 

    public Player() {
        name = "Player";
        store = new Store();
    }
    
    public Player(String name) {
        this.name = name;
        this.store = new Store();
    }
   
    public String getName() {
        return name;
    }

    public int getStoreCount() {
        return this.store.getStoneCount();
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store newStore){
        this.store = newStore;
    }

    @Override
    public String toString(){
        return name;
    }
}
