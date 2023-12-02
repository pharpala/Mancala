package mancala;

import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {

    
    private static final long serialVersionUID = 10;
    
    final private MancalaDataStructure gameBoard;
    private int currentPlayer = 1;
    final private Player player1 = new Player();
    final private Player player2 = new Player();

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        resetBoard();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }


    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    public MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(int pitNum) {
        if(pitNum < 0 || pitNum > 12){
            throw new PitNotFoundException();
        }
        int endIndex = pitNum;
        int startIndex;
        if (endIndex<=6) {
            startIndex = 0;
            endIndex = 6;
        } else {
            startIndex = 6;
            endIndex = 11;
        }
        
        for(int i = startIndex; i<= endIndex;i++){
            if(i==6 || i == 13) {
                continue;
            }
            if(gameBoard.getNumStones(i)>0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(Player one, Player two) {

        Store storeOne = new Store();
        Store storeTwo = new Store();
        storeOne.setOwner(one);
        storeTwo.setOwner(two);
        one.setStore(storeOne);
        two.setStore(storeTwo);
    
        getDataStructure().setStore(storeOne, 1); // Assigning the store of player one
        getDataStructure().setStore(storeTwo, 2); // Assigning the store of player two
    
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        for(int i= 1 ; i < 13 ; i++) {
            gameBoard.removeStones(i);
        }
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    @Override
    public String toString() {
        return " ";
    }
}
