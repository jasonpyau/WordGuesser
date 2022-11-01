import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  
import java.util.concurrent.TimeUnit.*;
import java.util.*;
import javax.swing.text.DefaultCaret;
import javax.imageio.ImageIO;
import java.io.*;

public class RemainingLetters extends FrameGui {
    
    int RemainingLettersItem;
    
    public RemainingLetters(int i) {
        RemainingLettersItem = i;
        RemainingLettersPanel.add(RemainingLetter);
        if (RemainingLettersItem == 26) {
            RemainingLetter.setText("ENT");
            RemainingLetter.setBackground(GREEN_COLOR);
        }
        else {
            RemainingLetter.setText(Character.toString((char)(i+65)));
            RemainingLetter.setBackground(DEFAULT_REMAINING_LETTER_COLOR);
        }
        RemainingLetter.setMargin(new Insets(0, 0, 0, 0));
        
        RemainingLetter.addActionListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(!ResultPanel.isVisible() && source == RemainingLetter) {
            if(RemainingLettersItem != 26) {
                keyPressed((char)(RemainingLettersItem+65));
            }
            else if (RemainingLettersItem == 26) {
                enterPressed();
            }
        }
    }
    
    
    public void updateRemainingLetterColor(Color colorSet) {
        if (RemainingLettersItem == 26) {
            return;
        }
        RemainingLetter.setBackground(colorSet);
    }
    
    public Color getColor() {
        Color color = RemainingLetter.getBackground();
        return color;
    }
    
    

    
    
    
    @Override
    public void updateScreen() {
        int minSize = Math.min(RemainingLetter.getWidth(), RemainingLetter.getHeight());
        RemainingLetter.setFont(RemainingLetter.getFont().deriveFont(Font.BOLD, (minSize+80)/12));
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