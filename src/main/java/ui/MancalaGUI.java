package ui;

import mancala.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MancalaGUI extends JFrame {
    private MancalaGame game;
    private GameRules rules;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Store store1;
    private Store store2;
    
    private JLabel turnLabel;
    private JLabel player1StoreLabel;
    private JLabel player2StoreLabel;
    private JButton[] pitButtons;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;

    public MancalaGUI() {
        initializeGame();

        // Create GUI components
        player1StoreLabel = new JLabel(player1.getName() + "'s Store: ");
        player2StoreLabel = new JLabel(player2.getName() + "'s Store: ");

        pitButtons = new JButton[12];
        for (int i = 0; i < 12; i++) {
            pitButtons[i] = new JButton("Pit " + (i + 1));
            final int pitNumber = i + 1;
            pitButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {    
                    handlePitButtonClick(pitNumber);
                }
            });
        }

        saveButton = new JButton("Save Game");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveButtonClick();
            }
        });

        loadButton = new JButton("Load Game");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLoadButtonClick();
            }
        });

        exitButton = new JButton("Exit Game");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExitButtonClick();
            }
        });

        turnLabel = new JLabel("Its your turn " + player1.getName());

        // Set up layout
        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(exitButton);
        saveLoadPanel.add(loadButton);

        // Set up layout
        setLayout(new BorderLayout());
        JPanel gamePanel = new JPanel(new GridLayout(2, 6));
        for (int i = 11; i >= 6; i--) {
            gamePanel.add(pitButtons[i]);
        }

        for (int i = 0; i < 6; i++) {
            gamePanel.add(pitButtons[i]);
        }

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(player1StoreLabel);
        infoPanel.add(player2StoreLabel);
        infoPanel.add(turnLabel);

        JPanel player1StorePanel = new JPanel(new GridLayout(2, 1));
        player1StorePanel.add(new JLabel("Store P1"));
        player1StorePanel.add(player1StoreLabel);

        JPanel player2StorePanel = new JPanel(new GridLayout(2, 1));
        player2StorePanel.add(new JLabel("Store P2: "));
        player2StorePanel.add(player2StoreLabel);

        // Add components to the main panel
        add(player1StorePanel, BorderLayout.EAST);
        add(gamePanel, BorderLayout.CENTER);
        add(player2StorePanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.SOUTH);
        add(saveLoadPanel, BorderLayout.NORTH);

        // Set default close operation and other properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mancala Game");
        setSize(600, 300);
        setLocationRelativeTo(null);

        // Update the GUI after components are added
        updateGUI();

        // Make the frame visible
        setVisible(true);
    }



    //Initialize game here
    private void initializeGame() {
        chooseRules();
        namePlayers();
        store1 = new Store();
        store2 = new Store ();

        game = new MancalaGame(rules);
        game.setPlayers(player1, player2);
        game.startNewGame();
    }


    //Helper method to allow user to choose rules
    private void chooseRules() {
        String[] options = {"AyoRules", "KalahRules"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose game rules:",
                "Game Rules",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    
        if (choice == 0) {
            rules = new AyoRules();
        } else {
            rules = new KalahRules();
        }
    }


    //Helper method to set player names
    private void namePlayers() {
        String name1 = JOptionPane.showInputDialog(this, "Enter name for Player 1:");
        String name2 = JOptionPane.showInputDialog(this, "Enter name for Player 2:");

        if (name1 != null && !name1.isEmpty()) {
            player1 = new Player(name1); 
        }

        if (name2 != null && !name2.isEmpty()) {
            player2 = new Player(name2);
        }
    }


    // Button methods go below
    private void handleSaveButtonClick() {
        try {
            Saver.saveObject(game, "savedGame.ser");
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving the game.");
        }
    }


    private void handleLoadButtonClick() {
        try {
            MancalaGame loadedGame = (MancalaGame) Saver.loadObject("savedGame.ser");
            game = loadedGame;
            currentPlayer = game.getCurrentPlayer();
            turnLabel.setText("Its your turn " + currentPlayer.getName());
            updateGUI();
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading the game.");
        }
    }


    private void handleExitButtonClick() {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit the game?", "Exit Game", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void handlePitButtonClick(int pitNumber) {
        try {
            game.move(pitNumber);
            Boolean finishGame = game.isGameOver();
            if (finishGame) {
                Player winner = game.getWinner();
                if (winner != null) {
                    JOptionPane.showMessageDialog(this, "Congratulations, " + winner.getName() + " wins!");
                } else {
                    JOptionPane.showMessageDialog(this, "It's a tie!");
                }
                initializeGame(); // Start a new game
                updateGUI();
            } else {
                currentPlayer = game.getCurrentPlayer();
                turnLabel.setText("It is your turn " + currentPlayer.getName());
            }
            updateGUI();
        } catch (InvalidMoveException | PitNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Invalid move. Please try again.");
        }
    }


    // Called each time a move is made
    private void updateGUI() {
        player1StoreLabel.setText(" " + rules.getDataStructure().getStoreCount(1));
        player2StoreLabel.setText(" " + rules.getDataStructure().getStoreCount(2));

        for (int i = 0; i < 6; i++) {
            pitButtons[i].setText("Pit " + (i + 1) + ": " + rules.getDataStructure().getNumStones(i+1));
        }

        for (int i = 6; i < 12; i++) {
            pitButtons[i].setText("Pit " + (i + 1) + ": " + rules.getDataStructure().getNumStones(i+1));
        }
    }


    //main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MancalaGUI::new);
    }
}
