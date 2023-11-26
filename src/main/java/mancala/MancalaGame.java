package mancala;

import java.io.Serializable;

public class MancalaGame implements Serializable {

    private static final long serialVersionUID = 10;
    private GameRules gameRules;
    private Player currentPlayer;
    private Player playerOne;
    private Player playerTwo;
    private MancalaDataStructure board = new MancalaDataStructure();

    public MancalaGame(GameRules gameRules) {
        this.gameRules = gameRules;
        this.playerOne = new Player("Player One");
        this.playerTwo = new Player("Player Two");
        this.currentPlayer = playerOne;

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
        return player.getStore().getStoneCount();
    }

    public Player getWinner() throws GameNotOverException {
        if (!isGameOver()) {
            throw new GameNotOverException();
        }

        int playerOneStones = playerOne.getStore().getStoneCount();
        int playerTwoStones = playerTwo.getStore().getStoneCount();

        if (playerOneStones < playerTwoStones) {
            return playerTwo;
        } else if (playerOneStones > playerTwoStones) {
            return playerOne;
        }
        return null;
    }

    public boolean isGameOver() {
        boolean emptyP2Pits = true;
        boolean emptyP1Pits = true;

        for (int i = 1; i <= 6; i++) {
            if (gameRules.getNumStones(i) != 0) {
                emptyP1Pits = false;
                break;
            }
        }

        for (int i = 7; i <= 12; i++) {
            if (gameRules.getNumStones(i) != 0) {
                emptyP2Pits = false;
                break;
            }
        }

        return emptyP1Pits || emptyP2Pits;
    }

    public int move(int startPit) throws InvalidMoveException {
        int stones = 0;
        int sumPit = 0;
        int playerNum;

        if (startPit < 1 || startPit > 12) {
            System.out.println("*******");
            throw new InvalidMoveException();
        }

        if(currentPlayer == playerOne) {
            playerNum = 1;
        } else {
            playerNum = 2;
        }
        
        stones = gameRules.moveStones(startPit, playerNum);

        if (stones == 0) {
            System.out.println("STONES EQUAL TO 0");
            throw new InvalidMoveException();
        }
        
        int lastPit = (stones + startPit % 14)-2;
        if(lastPit == 6 && currentPlayer == playerOne) {
            return stones; //repeat turn if landed on own pit
        }

        if(lastPit == 13 && currentPlayer == playerTwo) {
            return stones;
        }

        switchPlayer();
        return stones;
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
