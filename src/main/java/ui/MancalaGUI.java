package ui;

import mancala.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class MancalaGUI extends JFrame {
    private MancalaGame game;
    private MancalaDataStructure dataStruct;
    private ArrayList<Countable> data;
    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");

    private JLabel player1StoreLabel;
    private JLabel player2StoreLabel;
    private JButton[] pitButtons;

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

        add(gamePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mancala Game");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);
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
            int remainingStones = game.move(pitNumber);
            updateGUI();
            if (game.isGameOver()) {
                Player winner = game.getWinner();
                if (winner != null) {
                    JOptionPane.showMessageDialog(this, "Congratulations, " + winner.getName() + " wins!");
                } else {
                    JOptionPane.showMessageDialog(this, "It's a tie!");
                }
                initializeGame(); // Start a new game
                updateGUI();
            }
        } catch (InvalidMoveException | PitNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Invalid move. Please try again.");
        }
    }

    private void updateGUI() {
        player1StoreLabel.setText(player1.getName() + "'s Store: " + data.get(6).getStoneCount());
        player2StoreLabel.setText(player2.getName() + "'s Store: " + data.get(13).getStoneCount());

        for (int i = 0; i < 12; i++) {
            pitButtons[i].setText("Pit " + (i + 1) + ": " + data.get(i).getStoneCount());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MancalaGUI::new);
    }
}
