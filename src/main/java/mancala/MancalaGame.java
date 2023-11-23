package mancala;
import java.util.ArrayList;

public class MancalaGame{

    private Board board;
    private Player currentPlayer;
    private ArrayList<Player> players;

    public MancalaGame() {
        this.board = new Board(); //make a new board
        this.players = new ArrayList<>(); //create your player or players
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException {
        try {
            return this.board.getNumStones(pitNum); //get it from board class
        } catch (IndexOutOfBoundsException e) {
            throw new PitNotFoundException("Pit " + pitNum + " not found.");
        }
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }
    
    public int getStoreCount(Player player) throws NoSuchPlayerException {
    if (!this.players.contains(player)) {
        throw new NoSuchPlayerException("Player not found in the game.");
    }
        return player.getStoreCount();
    }

    public Player getWinner() throws GameNotOverException,PitNotFoundException {
    if (!isGameOver()) {
        throw new GameNotOverException("The game is not over yet.");
    }
        return currentPlayer;
    }

    public boolean isGameOver() throws PitNotFoundException { //check if game is over
        return (this.board.isSideEmpty(1) || this.board.isSideEmpty(7));
    }

    public int move(int startPit) throws InvalidMoveException, PitNotFoundException {
        if(startPit >12 || startPit <1) {
            throw new InvalidMoveException("Invalid move: try again");
        }

        if(this.currentPlayer.equals(players.get(0))) {
            if(startPit>6) {
                throw new InvalidMoveException("Invalid move: try again");
            }
        }

        if(this.currentPlayer.equals(players.get(1))) {
            if(startPit<7) {
                throw new InvalidMoveException("Invalid move: try again");
            }
        }

        int stonesToAdd= board.getNumStones(startPit-1);
        int originalStart = startPit;
        int checker = (originalStart + stonesToAdd);

        int val = this.board.moveStones(startPit, this.currentPlayer);
        if(originalStart <7) {
            if(checker == 7) {
                return val; //player gets another turn
            }
        }
        if(originalStart < 13 && originalStart > 6) {
            if(checker == 13) {
                return val; //player gets another turn
            }
        }

        this.currentPlayer = (this.currentPlayer == this.players.get(0)) ? this.players.get(1) : this.players.get(0);
        return val;
    }

    public void setBoard(Board theBoard) { //sets the board for the game
        this.board = theBoard;
    }

    public void setCurrentPlayer(Player player) { //sets the current player
        this.currentPlayer = player;
    }

    public void setPlayers(Player onePlayer, Player twoPlayer) { //set players
        this.players.add(onePlayer);
        this.players.add(twoPlayer);
        this.currentPlayer = this.players.get(0);
        this.board.registerPlayers(onePlayer, twoPlayer);
    }

    public void startNewGame() { //starts a new game by resetting board
        this.board = new Board();
    }

    @Override
    public String toString() {
        return "MancalaGame: " + players.get(0).getName() + " vs. " + players.get(1).getName();
    }

}
