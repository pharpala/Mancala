package mancala;

public class Player {

    private Store userStore;
    private String userName;

    public Player() { //initialize a new Player
        this.userStore = new Store();
        this.userName = "Player";
    }

    public Player(String name) { //initialize a new player with a name
        this.userStore = new Store();
        this.userName = name;
    }

    public String getName() { //gets the name of the players
        return this.userName;
    }

    public Store getStore() { //gets the players store
        return this.userStore;
    }

    public void setStore(Store store) {
        this.userStore = store;
        //System.out.println("Setting store for player " + this.userName + ": " + store.getOwner());
    }

    @Override
    public String toString() {
        return "Player: " + userName + ", Store: " + userStore.toString();
    }
}