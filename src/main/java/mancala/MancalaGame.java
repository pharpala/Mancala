package mancala;

import java.io.Serializable;
import java.util.ArrayList;

public class MancalaGame implements Serializable {

    private static final long serialVersionUID = 10;
    private GameRules gameRules;
    private Player currentPlayer;
    private Player playerOne;
    private Player playerTwo;
    private MancalaDataStructure board = new MancalaDataStructure();

    public MancalaGame(GameRules gameRules) { //constructor sets players, currPlayer, registers and starts
        currentPlayer = playerOne;
        playerOne = new Player("Player One");
        playerTwo = new Player("Player Two");
        gameRules.registerPlayers(playerOne, playerTwo);
        startNewGame();
    }

    public GameRules getBoard() {
       return this.gameRules; 
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException {
        if (pitNum > 0 && pitNum < 13) {     
            return this.gameRules.getNumStones(pitNum);
        } else {
            throw new PitNotFoundException();
        }
    }
   
    public int getStoreCount(Player player) throws NoSuchPlayerException {
        if (player != playerTwo && player != playerOne){ //check if its player or currentPlayer
            throw new NoSuchPlayerException();
        }
        return player.getStore().getStoneCount();
    }

    public Player getWinner() throws GameNotOverException {
        if (!isGameOver()) {
            throw new GameNotOverException();
        }

        //total number of stones for each player
        int playerOneStones = playerOne.getStore().getStoneCount();
        int playerTwoStones = playerTwo.getStore().getStoneCount();

        if(playerOneStones < playerTwoStones){
            return playerTwo;
        } else if(playerOneStones > playerTwoStones){
            return playerOne;
        }
        return null; // return null if draw
    }

    public boolean isGameOver() {
        boolean emptyP2Pits = true;
        boolean emptyP1Pits = true;
       
        //check pits for player one
        for (int i = 1; i < 7; i++) {
            if (gameRules.getNumStones(i) != 0) {
                emptyP1Pits = false;
                break;
            }
        }

        //p2
        for (int i = 7; i < 13; i++) {
            if (gameRules.getNumStones(i) != 0) {
                emptyP2Pits = false;
                break;
            }
        }

        //if all pits for either player are empty, the game is over
        return true;
    }

    public int move(int startPit) throws InvalidMoveException {

        int stones = 0;
        int sumPit = 0;

        // Check if the startPit is within the valid range (0 to 11)
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException();
        }

        int playerNum = (currentPlayer == playerOne) ? 1 : 2;
        stones = gameRules.moveStones(startPit, playerNum);
        // Attempt to move stones on the board
        //stones = gameRules.moveStones(startPit , playerNum) called twice

        // If stones were moved, switch to the next player
        if (stones > 0) {
            switchPlayer();
        }

        // Calculate the sum of stones in pits based on the current player and startPit
        if (startPit > 1 && startPit < 7 && currentPlayer == playerOne) {
            for (int i = 1; i < 7; i++) {
                sumPit += board.getNumStones(i);
            }

        } else if (startPit > 6 && startPit < 13 && currentPlayer == playerTwo) {
            for (int i = 7; i < 13; i++) {
                sumPit += board.getNumStones(i);
            }
        }
        // Return the sum of stones in pits
        return sumPit;
        }

    public void setBoard(GameRules theBoard) {
        // Set the game board to the provided board
        this.gameRules = theBoard;
    }

    public void setCurrentPlayer(Player player) {
        // Set the current player to the provided player
        this.currentPlayer = player;
    }

    public void setPlayers(Player onePlayer, Player twoPlayer) {
        this.playerOne = onePlayer; // Assign the first player to playerOne and the second player to playerTwo.
        this.playerTwo = twoPlayer;
        // Register the players with the game board.
        this.gameRules.registerPlayers(playerOne, playerTwo);
    }

    public void startNewGame() {
        this.gameRules.resetBoard();  //reset the board and assign playerOne to start
        this.currentPlayer = playerOne;
    }

    public void switchPlayer() {
        if (this.currentPlayer == playerOne) {
            this.currentPlayer = playerTwo; //switch Players
        } else {
            this.currentPlayer = playerOne;
        }
    }
    
    @Override
    public String toString() {
        return "MancalaGame: " + playerOne.getName() + " vs. " + playerTwo.getName();
    }
}