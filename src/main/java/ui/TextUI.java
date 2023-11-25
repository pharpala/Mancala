package ui;
import java.util.ArrayList;
import java.util.Scanner;
import mancala.*;

public class TextUI {
    private Scanner scanner = new Scanner(System.in);
    private MancalaGame myGame;
    private MancalaDataStructure board = new MancalaDataStructure();
    private int runTime = 0;
    private ArrayList<Countable> data = board.AList();

    public void getInfo(){

        if(runTime == 0) {
            board.setUpPits();
            runTime =1 ;
        }

        System.out.println("Player Two's Store : ");
        for (int i = 12; i > 6; i--) {
            System.out.print("   " + data.get(i).getStoneCount() + " ");
        }
        System.out.println();

        System.out.println("[" + data.get(13).getStoneCount() + "] ---- ---- ---- ---- ---- ---- [" + data.get(6).getStoneCount() + "]");
        System.out.print("   ");

         for (int i = 0; i < 6; i++) {
            System.out.print("   " + data.get(i).getStoneCount() + " ");
        }

        System.out.println();
        System.out.println("                                Player One's Store");
        System.out.println();
    }

    public void gameToBegin() {
        System.out.println("Welcome to Mancala!");

         // Prompt the user to choose the game rules
        System.out.println("Choose the game rules: Enter 'Ayo' for Ayo Rules or 'Kalah' for Kalah Rules");
        String rulesChoice = scanner.nextLine();

        GameRules rules;
        if ("Ayo".equalsIgnoreCase(rulesChoice)) {
            rules = new AyoRules(board);
        } else if ("Kalah".equalsIgnoreCase(rulesChoice)) {
            rules = new KalahRules(board);
        } else {
            System.out.println("Invalid choice. Exiting...");
            scanner.close();
            return;
        }

        myGame = new MancalaGame(rules);
        // Set up players
        System.out.print("Enter Player One's name: ");
        String playerOneName = scanner.nextLine();
        Player playerOne = new Player(playerOneName);

        System.out.print("Enter Player Two's name: ");
        String playerTwoName = scanner.nextLine();
        Player playerTwo = new Player(playerTwoName);

        // Set up the myGame with players
        myGame.setPlayers(playerOne, playerTwo);
        myGame.startNewGame();

        // Main myGame loop
        while (!myGame.isGameOver()) {
            System.out.println("Current board:");
            getInfo();
            Player currentPlayer = myGame.getCurrentPlayer();
            System.out.println("It's " + currentPlayer.getName() + "'s turn.");

            // Prompt the current player to enter the pit number to move from
            if (currentPlayer == playerOne) {
                System.out.print("Enter the pit number to move from (1-6): ");
            } else if (currentPlayer == playerTwo) {
                System.out.print("Enter the pit number to move from (7-12): ");
            }

            // Get the input for the pit number
            try {
                startPit = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            // Try to make a move and handle exceptions
            try {
                int stonesMoved = myGame.getNumStones(startPit-1);
                myGame.move(startPit);
                System.out.println(currentPlayer.getName() + " moved " + stonesMoved + " stones.");
            } catch (PitNotFoundException e) {
                System.out.println("Invalid pit. " + e.getMessage());
            }catch (InvalidMoveException e){
                System.out.println("Invalid move. " + e.getMessage());
            }

            System.out.println();
        }

        // Game over, print the final results
        System.out.println("Game Over!");
        getInfo();
        Player winner = myGame.getWinner();
        if (winner != null) {
            System.out.println(winner.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

public static void main(String[] args) {
        TextUI textUI = new TextUI();
        textUI.gameToBegin();
}
}