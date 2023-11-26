package ui;

import mancala.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    private MancalaGame game;
    private MancalaDataStructure dataStruct;
    private Scanner scanner;
    private ArrayList<Countable> data;
    Player player1 = new Player("Player 1");
    Player player2 = new Player("Player 2");
    
    public TextUI() {
        scanner = new Scanner(System.in);
        dataStruct = new MancalaDataStructure();
        dataStruct.setUpPits();
        data = dataStruct.getData(); //PITS AND STORES
        dataStruct.setStore(data.get(6), 1);
        dataStruct.setStore(data.get(13), 2);
    }

 public void runGame() {
    try {
        System.out.println("Welcome to my Mancala!");
        System.out.println("Choose the game rules: Enter 'Ayo' for Ayo Rules or 'Kalah' for Kalah Rules");
        String rulesChoice = scanner.nextLine();

        GameRules rules;
        if ("Ayo".equalsIgnoreCase(rulesChoice)) {
            rules = new AyoRules(dataStruct);
        } else if ("Kalah".equalsIgnoreCase(rulesChoice)) {
            rules = new KalahRules(dataStruct);
        } else {
            System.out.println("Invalid choice. Playing Kalah...");
            rules = new KalahRules(dataStruct);
        }

        game = new MancalaGame(rules);

        game.setPlayers(player1, player2);
        game.startNewGame();
        while (true) {
            displayBoard();
            Player currentPlayer = game.getCurrentPlayer();

            if (currentPlayer == player1) {
                System.out.println(player1.getName() + ", it's your turn.\n");
                System.out.print("Enter the pit number to move stones (1-6): \n");
            } else {
                System.out.println(player2.getName() + ", it's your turn.\n");
                System.out.print("Enter the pit number to move stones (7-12): \n");
            }

            int pitNumber = scanner.nextInt();
            if(pitNumber > 5) {
                pitNumber = pitNumber+1;
            }
            
            try {
                int remainingStones = game.move(pitNumber);
                if (game.isGameOver()) {
                    displayBoard();
                    Player winner = game.getWinner();
                    if (winner != null) {
                        System.out.println("Congratulations, " + winner.getName() + " wins!");
                    } else {
                        System.out.println("It's a tie!");
                    }
                    break;
                }
            } catch (InvalidMoveException | PitNotFoundException e) {
                System.out.println("Invalid move. Please try again.");
            }
        }
    } catch (GameNotOverException e) {
        System.out.println("The game is not over yet.");
    }

    scanner.close();
}


    private void displayBoard() {
    System.out.println("Current Mancala Board State:");

    // Get the list of pits and stores from the game board

    // Display the player stores
    System.out.println(player1.getName() + "'s Store: " + data.get(6).getStoneCount());
    System.out.println(player2.getName() + "'s Store: " + data.get(13).getStoneCount()); //STORE 1

    // Display the pits for Player 1 (indices 0 to 5)
    for (int i = 0; i < 6; i++) {
        System.out.print("Pit " + (i + 1) + ": " + data.get(i).getStoneCount() + "   ");
    }
    
    // Display Store 1 for Player 1
    System.out.print("Store 1: " + data.get(6).getStoneCount() + "   ");
    System.out.print("\n");
    // Display the pits for Player 2 (indices 7 to 12)
    for (int i = 6; i < 12; i++) {
        System.out.print("Pit " + (i + 1) + ": " + data.get(i+1).getStoneCount() + "   ");
    }
    
    // Display Store 2 for Player 2
    System.out.print("Store 2: " + data.get(13).getStoneCount() + "   ");
    
    System.out.println();
}

    public static void main(String[] args) {
        TextUI testUI = new TextUI();
        testUI.runGame();
    }
}