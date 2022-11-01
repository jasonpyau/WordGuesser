import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  
import java.util.concurrent.TimeUnit.*;
import java.util.*;
import java.lang.*;

public class GuessDisplay extends FrameGui implements ActionListener {

    int GuessDisplayX;
    int GuessDisplayY;
    int GuessDisplayLength;
    int GuessDisplayItem;
    
    public GuessDisplay(int i) {
        GuessDisplayItem = i;
        
        Frame.add(GuessDisplay);
        Frame.add(PlaceHolder);
        
        GuessDisplay.setVisible(false);
        RemainingLetter.setMargin(new Insets(0, 0, 0, 0));
        PlaceHolder.setVisible(false);
        updateScreen();

        GuessDisplay.addActionListener(this);
        
        

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(!ResultPanel.isVisible() && currentRow == GuessDisplayItem/5 && source == GuessDisplay) {
            GuessDisplays[currentRow][currentChar].updateDisplayColor(DEFAULT_GUESS_COLOR);
            updateDisplayColor(CURRENT_GUESS_COLOR);
            currentChar = GuessDisplayItem%5;
            lastChar = (currentChar == 4) ? currentChar-1 : currentChar;
        }


    }

    public void GuessDisplayVisible(boolean visible) {
        if (visible) {
            GuessDisplay.setVisible(true);
            updateScreen();
            updateDisplayColor(DEFAULT_GUESS_COLOR);

        }
        else {
            GuessDisplay.setVisible(false);
            updateScreen();
        }
    }
    
    public void updateDisplayText(char letter) {
        GuessDisplay.setText(String.valueOf(letter));
    }
    
    public char getDisplayText() {
        return GuessDisplay.getText().charAt(0);
    }
    
    public void updateDisplayColor(Color colorSet) {
        GuessDisplay.setBackground(colorSet);
    }

    @Override
    public void updateScreen() {
    
        int PanelX = GuessDisplayPanel.getX(); //DELETE WHEN IN PANEL
        int PanelWidth = GuessDisplayPanel.getWidth(); 
        GuessDisplayX = (PanelX+(GuessDisplayItem%5+1)*((PanelWidth/6)+PanelWidth/250)-PanelWidth/12-PanelWidth/100);
        GuessDisplayY = height/12+((GuessDisplayItem/5)*PanelWidth/5);
        GuessDisplayLength = PanelWidth/6;//Math.min(width,height)/8;
        int fontSize = (GuessDisplayLength+100)/12;
        GuessDisplay.setFont(GuessDisplay.getFont().deriveFont(Font.BOLD, fontSize));
        GuessDisplay.setBounds(GuessDisplayX, GuessDisplayY, GuessDisplayLength, GuessDisplayLength);
        
    }

    
    @Override
    public void checkResolutionChange() {

    }
    
    @Override
    public void newGuessDisplay() {
        
    }
    
    @Override
    public void newRemainingLetters() {
        
    }
    @Override
    public void newStatDisplays() {
        
    }
    
    @Override
    public void newHelpDisplay() {
        
    }

}