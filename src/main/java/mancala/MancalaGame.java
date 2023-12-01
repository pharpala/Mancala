package mancala;

import java.io.Serializable;

public class MancalaGame implements Serializable {

    private static final long serialVersionUID = 10;
    private GameRules gameRules;
    private Player currentPlayer;
    private Player playerOne;
    private Player playerTwo;
    private MancalaDataStructure board;

    public MancalaGame(GameRules gameRules) {
        this.gameRules = gameRules;
        this.playerOne = new Player("Player One");
        this.playerTwo = new Player("Player Two");
        this.currentPlayer = playerOne;
    }

    public GameRules getBoard() {
        return this.gameRules;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException {
        if (pitNum >= 1 && pitNum <= 12) {
            return this.gameRules.getNumStones(pitNum);
        } else {
            throw new PitNotFoundException();
        }
    }

    public int getStoreCount(Player player) throws NoSuchPlayerException {
        if (player != playerTwo && player != playerOne) {
            throw new NoSuchPlayerException();
        }
        return player.getStoreCount();
    }

    public Player getWinner() throws GameNotOverException {
        if (!isGameOver()) {
            throw new GameNotOverException();
        }

        int playerOneStones = playerOne.getStoreCount();
        int playerTwoStones = playerTwo.getStoreCount();

        if (playerOneStones < playerTwoStones) {
            return playerTwo;
        } else if (playerOneStones > playerTwoStones) {
            return playerOne;
        }
        return null;
    }

    public boolean isGameOver() {

        for (int i = 1; i <= 6; i++) {
            if (getNumStones(i) != 0) {
                return false;
            }
        }

        for (int i = 7; i <= 12; i++) {
            if (getNumStones(i) != 0) {
                return false;
            }
        }

        return true;
    }

    public int move(int startPit) throws InvalidMoveException {
        int val=0;
        int stonesToAdd=0;

        if(startPit >12 || startPit <1) {
            throw new InvalidMoveException("Invalid move: try again");
        }

        if(this.currentPlayer.equals(playerOne)) {
            if(startPit>6) {
                throw new InvalidMoveException("Invalid move: try again");
            } 
        }

        if(this.currentPlayer.equals(playerTwo)) {
            if(startPit<7) {
                throw new InvalidMoveException("Invalid move: try again");
            } 
        }
        stonesToAdd= getNumStones(startPit);
        int lastPitLand = (stonesToAdd + startPit) %14;

        if(this.currentPlayer.equals(playerOne)) {
            val = gameRules.moveStones(startPit, 1); //playerNum is 1 
            //System.out.println(val);
            if(lastPitLand == 7) {
                return val; //player gets another turn
            }
        }

        if(this.currentPlayer.equals(playerTwo)) {
            val = gameRules.moveStones(startPit, 2); //playerNum is 2
            //System.out.println(val);
            if(lastPitLand == 13) {
                return val; //player gets another turn
            }
        }

        switchPlayer();
        return val;
    }

    public void setBoard(GameRules theBoard) {
        this.gameRules = theBoard;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public void setPlayers(Player onePlayer, Player twoPlayer) {
        this.playerOne = onePlayer;
        this.playerTwo = twoPlayer;
        this.gameRules.registerPlayers(playerOne, playerTwo);
    }

    public void startNewGame() {
        this.gameRules.resetBoard();
        this.currentPlayer = playerOne;
    }

    public void switchPlayer() {
        this.currentPlayer = (this.currentPlayer == playerOne) ? playerTwo : playerOne;
    }

    @Override
    public String toString() {
        return "MancalaGame: " + playerOne.getName() + " vs. " + playerTwo.getName();
    }
}
