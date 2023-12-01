package ui;

import mancala.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MancalaGUI extends JFrame {
    private MancalaGame game;
    private MancalaDataStructure dataStruct;
    private ArrayList<Countable> data;
    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");
    private Player currentPlayer;
    private JLabel turnLabel;

    private JLabel player1StoreLabel;
    private JLabel player2StoreLabel;
    private JButton[] pitButtons;
    private JButton saveButton;
    private JButton loadButton;

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
                    if(pitNumber <7) {
                        handlePitButtonClick(pitNumber);
                    } else {
                        handlePitButtonClick(pitNumber+1);
                    }

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

        turnLabel = new JLabel("Its your turn " + player1.getName());

        // Set up layout
        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(loadButton);

        // Set up layout
        setLayout(new BorderLayout());
        JPanel gamePanel = new JPanel(new GridLayout(2, 6));
        for (int i = 0; i < 6; i++) {
            gamePanel.add(pitButtons[i]);
        }
        for (int i = 11; i >= 6; i--) {
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

    private void handleSaveButtonClick() {
        try {
            Saver.saveObject(game, "savedGame.ser");
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving the game.");
        }
    }

    private void handleLoadButtonClick() {
        try {
            MancalaGame loadedGame = (MancalaGame) Saver.loadObject("savedGame.ser");
            game = loadedGame;
            currentPlayer = game.getCurrentPlayer();
            turnLabel.setText("Turn: " + currentPlayer.getName());
            updateGUI();
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading the game.");
        }
    }

    private void initializeGame() {
        dataStruct = new MancalaDataStructure();
        dataStruct.setUpPits();
        data = dataStruct.getData();
        dataStruct.setStore(data.get(6), 1);
        dataStruct.setStore(data.get(13), 2);

        GameRules rules = new KalahRules(dataStruct); // Default to Kalah rules
        game = new MancalaGame(rules);
        game.setPlayers(player1, player2);
        game.startNewGame();
    }

    private void handlePitButtonClick(int pitNumber) {
        try {
            game.move(pitNumber);
            if (game.isGameOver()) {
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
                turnLabel.setText("Turn: " + currentPlayer.getName());
            }
            updateGUI();
        } catch (InvalidMoveException | PitNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Invalid move. Please try again.");
        }
    }

    private void updateGUI() {
        player1StoreLabel.setText(" " + data.get(6).getStoneCount());
        player2StoreLabel.setText(" " + data.get(13).getStoneCount());

        for (int i = 0; i < 6; i++) {
            pitButtons[i].setText("Pit " + (i + 1) + ": " + data.get(i).getStoneCount());
        }

        for (int i = 6; i < 12; i++) {
            pitButtons[i].setText("Pit " + (i + 1) + ": " + data.get(i+1).getStoneCount());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MancalaGUI::new);
    }
}
