/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunter.view;

import hunter.model.Model;
import hunter.model.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Meeoro
 * Class for the main window for the Hunter game
 */
public class MainWindow extends JFrame{

    private int size;
    private int moves;
    private Model model;
    private JMenuBar menuBar = new JMenuBar();
    private JLabel currentPlayerLabel;
    private JLabel movesLabel;
    private final JButton[][] grid = new JButton[7][7];
    
    private JMenu gameMenu = new JMenu("Boards");
    private JMenuItem start3x3 = new JMenuItem("3x3");
    private JMenuItem start5x5 = new JMenuItem("5x5");
    private JMenuItem start7x7 = new JMenuItem("7x7");

    
    /**
     * 
     * @param size the size of the game board (3x3, 5x5, 7x7) 
     * automatically sets the number of moves based on the board size
     * 
     * main window is separated into top and center where 
     * top contains the game information (currentPlayer, numberOfMovesLeft, newGame button)
     * and center contains the game itself
     */
    public MainWindow(int size) {
        this.size = size;
        switch(size){
                case(3):
                    moves = 12;
                    break;
                case(5):
                    moves = 20;
                    break;
                case(7):
                    moves = 28;
                    break;
        }
        model = new Model(size, moves);
        
        setTitle("Hunter game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel top = new JPanel(new GridLayout(1, 3, 20, 20));
        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
        movesLabel = new JLabel();
        movesLabel.setHorizontalAlignment(JLabel.CENTER);
        updateLabelText();
        
        JButton newGameButton = new JButton();
        newGameButton.setText("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame(size, moves);
            }
        });
        
        top.add(currentPlayerLabel);
        top.add(newGameButton);
        top.add(movesLabel);

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));
        
        generatePlayField(mainPanel);

        
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        

        setJMenuBar(menuBar);
        
        menuBar.add(gameMenu);
        gameMenu.add(start3x3);
        gameMenu.add(start5x5);
        gameMenu.add(start7x7);

        /**
         * ActionListener for creating a 3 by 3 board
         */
        start3x3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start3x3_actionPerformed(e);
            }
        });
        
        /**
         * ActionListener for creating a 5 by 5 board
         */
        start5x5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start5x5_actionPerformed(e);
            }
        });
        
        /**
         * ActionListener for creating a 7 by 7 board
         */
        start7x7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start7x7_actionPerformed(e);
            }
        });
    }
    
    /**
     * 
     * Creating a 3 by 3 board 
     */
    private void start3x3_actionPerformed(ActionEvent e){
        size = 3;
        MainWindow newWindow = new MainWindow(3);
        newWindow.setVisible(true);
        
        this.dispose();
        newGame(size, moves);

    }

    /**
     * 
     * Creating a 5 by 5 board 
     */
    private void start5x5_actionPerformed(ActionEvent e){
        size = 5;
        MainWindow newWindow = new MainWindow(5);
        newWindow.setVisible(true);
        
        this.dispose();
        newGame(size, moves);
    }
        
    /**
     * 
     * Creating a 7 by 7 board 
     */
    private void start7x7_actionPerformed(ActionEvent e){
        size = 7;
        MainWindow newWindow = new MainWindow(7);
        newWindow.setVisible(true);
        
        this.dispose();
        newGame(size, moves);
    }

    
    /**
     * Adds or updates a button at the desired place on a GridLayout table
     * 
     * @param panel the panel where we want to update the button
     * @param i row of the grid table
     * @param j column of the grid table
     * 
     * 
     * handles the mouseclicks and checks for winners
     */
    private void addButton(JPanel panel, int i, int j) {

        grid[i][j] = new JButton();
        panel.add(grid[i][j]);

        
        grid[i][j].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (model == null) {
                    return;
                }
                
                if (e.getButton() == MouseEvent.BUTTON1){
                    model.move(i, j);
                    updateAllButtons();
                    updateLabelText();
                }
                Player winner = model.checkWinner(i, j);
                if (winner != Player.NOBODY){
                    showWinnerMessage(winner);
                    newGame(size, moves);
                }
            }
        });
    }
    
    
    /**
     * Generates the buttons on the main game panel based on the size of the board
     * 
     * @param panel the panel where we want to add the buttons 
     */
    private void generatePlayField(JPanel panel){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                addButton(panel, i, j);

            }
        }
        updateAllButtons();
        updateLabelText();
    }
    
    /**
     * Shows the winner message
     * @param winner 
     */
    private void showWinnerMessage(Player winner){
        JOptionPane.showMessageDialog(this, winner.name() + " wins");
        newGame(size, moves);
    }
    
    /**
     * Creates a new board with the current size
     * @param newsize
     * @param newmoves 
     */
    private void newGame(int newsize, int newmoves){
        model = new Model(size, moves);
        updateAllButtons();
        updateLabelText();
    }
    
    /**
     * Updates the text on the top part of the main panel
     */
    private void updateLabelText() {
        currentPlayerLabel.setText("Current player: " + model.getCurrentPlayer().name());
        movesLabel.setText("Number of moves remaining: " + model.getNumberOfMovesLeft());
    }
    
    /**
     * Updates the buttons on the game board
     * sets the highlighted buttons to green
     * sets the player buttons to default
     * sets the empty buttons to gray
     */
    private void updateAllButtons(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                Player text = model.getPlayerAt(i, j);
                if (text.equals(Player.HIGHLIGHTED) ){
                    //grid[i][j].setText("H");
                    grid[i][j].setBackground(Color.green);
                }
                else if(text == Player.NOBODY){
                    grid[i][j].setText("");
                    grid[i][j].setBackground(null);
                }
                else {
                    grid[i][j].setText(text.name());
                    grid[i][j].setBackground(Color.lightGray);
                }
            }
        }
    }
    
    
}
