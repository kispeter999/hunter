/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunter.model;

/**
 *
 * @author Meeoro
 * Class for the model of the Hunter game
 */
public class Model {
    
    private int size;
    private int numberOfMovesLeft;
    private Player currentPlayer;
    private int[] selectedObject = new int[2];
    private Player[][] table;
    
    /**
     * Creates the model for the current game based on size
     * @param size size of the current board (3x3, 5x5, 7x7)
     * @param moves the number of moves left based on the board size
     */
    public Model(int size, int moves) {
        this.size = size;
        this.numberOfMovesLeft = moves;

        currentPlayer = Player.HUNTER;
        
        table = new Player[size][size];
        
        //setting the field to empty
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = Player.NOBODY;
            }
        }
        
        //setting the objects for hunter and hunted
        table[0][0] = Player.HUNTER;
        table[0][size-1] = Player.HUNTER;
        table[size-1][0] = Player.HUNTER;
        table[size-1][size-1] = Player.HUNTER;
        
        int middlePoint = (int)(size/2);
        table[middlePoint][middlePoint] = Player.HUNTED;
        
        
    }
    

    /**
     * Method for moving a player on the game board
     * @param row row of the selected player on the gridLayout table
     * @param column column of the selected player on the gridLayout table
     * @return a Player object
     * 
     * also highlights the possible moves for the selected player
     * and switches players when one took action
     */
    public Player move(int row, int column) {
        if (currentPlayer == Player.HUNTER){

            if (table[row][column] == Player.HUNTER){
                revertHighlighted();
                selectedObject[0] = row;
                selectedObject[1] = column;
                highlightPossibleMoves(selectedObject);
            }
            
            else if (table[row][column] == Player.HIGHLIGHTED){
                table[selectedObject[0]][selectedObject[1]] = Player.NOBODY;
                table[row][column] = currentPlayer;
                
                numberOfMovesLeft--;
                revertHighlighted();
                switchPlayers();
            }
            else{
                revertHighlighted();
            }
        }
        
        else if (currentPlayer == Player.HUNTED){
            if (table[row][column] == Player.HUNTED){
                selectedObject[0] = row;
                selectedObject[1] = column;
                highlightPossibleMoves(selectedObject);
            }
            
            else if (table[row][column] == Player.HIGHLIGHTED){
                table[selectedObject[0]][selectedObject[1]] = Player.NOBODY;
                table[row][column] = currentPlayer;
                
                revertHighlighted();
                switchPlayers();
            }
            else{
                revertHighlighted();
            }
        }
        
        return table[row][column];
    }
    
    /**
     * Method for checking if a player has won the game
     * @param row row of the selected player on the gridLayout table
     * @param column column of the selected player on the gridLayout table
     * @return the winner if there is one, returns PLAYER.NOBODY if no winner
     */
    public Player checkWinner(int row, int column){
        if (currentPlayer == Player.HUNTER && numberOfMovesLeft == 0) {
            return Player.HUNTED;
        }
        //checks if the hunted player is able to move
        //(has free space above/under/next to it)
        if (currentPlayer == Player.HUNTED){
            boolean canMove = false;
            
            //felette
            if (row > 0){
                if (table[row-1][column] != Player.HUNTER){
                    canMove = true;
                }
            }
            //alatta
            if ((row+1) < size){
                if (table[row+1][column] != Player.HUNTER){
                    canMove = true;
                }
            }
            //balra
            if (column > 0){
                if (table[row][column-1] != Player.HUNTER ){
                    canMove = true;
                }
            }
            //jobbra
            if ((column+1) < size){
                if (table[row][column+1] != Player.HUNTER){
                    canMove = true; 
                }
            }
            if (canMove) return Player.NOBODY;
            else return Player.HUNTER;
        }
        return Player.NOBODY;
    }
    
    /**
     * 
     * @return current player 
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * returns the Player at the selected point on the table
     * @param i = row
     * @param j = column
     * @return 
     */
    public Player getPlayerAt(int i, int j){
        return table[i][j];
    }
    
    /**
     * returns the number of moves left for the Hunter
     * @return 
     */
    public int getNumberOfMovesLeft(){
        return numberOfMovesLeft;
    }
    
    /**
     * switches players
     */
    public void switchPlayers() {
        if (currentPlayer == Player.HUNTER) {
            currentPlayer = Player.HUNTED;
        } else {
            currentPlayer = Player.HUNTER;
        }
    }
    
    /**
     * highlights the possible moves for a Player
     * @param x a point on the grid table
     */
    public void highlightPossibleMoves(int[] x) {
        //felette
        if (x[0] > 0){
            if (table[x[0]-1][x[1]] == Player.NOBODY){
                table[x[0]-1][x[1]] = Player.HIGHLIGHTED;
            }
            
        }
        //alatta
        if ((x[0]+1) < size){
            if (table[x[0]+1][x[1]] == Player.NOBODY){
                table[x[0]+1][x[1]] = Player.HIGHLIGHTED;
            }
            
        }
        //balra
        if (x[1] > 0){
            if (table[x[0]][x[1]-1] == Player.NOBODY){
                table[x[0]][x[1]-1] = Player.HIGHLIGHTED;
            }
            
        }
        //jobbra
        if ((x[1]+1) < size){
            if (table[x[0]][x[1]+1] == Player.NOBODY){
                table[x[0]][x[1]+1] = Player.HIGHLIGHTED; 
            }
            
        }
    }
    
    /**
     * reverts the highlights made by highlightPossibleMoves method
     */
    public void revertHighlighted() {
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (table[i][j] == Player.HIGHLIGHTED){
                    table[i][j] = Player.NOBODY;
                }
            }
        }
    }
}
