/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunter;

import hunter.view.MainWindow;

/**
 *
 * @author Meeoro
 */
public class Hunter {

    /**
     * Creating the main window for the Hunter game with the specified board size
     */
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow(7);
        mainWindow.setVisible(true);
    }
    
}
