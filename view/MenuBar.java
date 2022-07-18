/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunter.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author Meeoro
 */
public class MenuBar extends JMenuBar {
    private int size = 5;
    
    public MenuBar(){
        
        JMenu gameMenu = new JMenu("Boards");
        
        JMenuItem startNewGame = new JMenuItem("New Game");
        //startNewGame.setText("Start");
        
        gameMenu.add(startNewGame);
        gameMenu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        
        JRadioButtonMenuItem three = new JRadioButtonMenuItem();
        three.setText("3x3");
        three.setSelected(true);
        three.addActionListener(actionListener);
        three.setActionCommand("3");
        group.add(three);
        
        JRadioButtonMenuItem five = new JRadioButtonMenuItem();
        five.setText("5x5");
        five.addActionListener(actionListener);
        five.setActionCommand("5");
        group.add(five);
        
        JRadioButtonMenuItem seven = new JRadioButtonMenuItem();
        seven.setText("7x7");
        seven.addActionListener(actionListener);
        seven.setActionCommand("7");
        group.add(seven);
    }
    
    private ActionListener actionListener = new ActionListener(){
        
        @Override
        public void actionPerformed(ActionEvent e){
            String actionCommand = e.getActionCommand();
            size = Integer.parseInt(actionCommand);
        }
    };
    
    public int getsize(){
        return size;
    }
}
