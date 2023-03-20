import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  

public class RemainingLetters implements ActionListener{
    
    int RemainingLettersItem;
    public JButton RemainingLetter = new JButton(" ");
    
    public RemainingLetters(int i) {
        RemainingLettersItem = i;
        FrameGui.RemainingLettersPanel.add(RemainingLetter);
        if (RemainingLettersItem == 26) {
            RemainingLetter.setText("ENT");
            RemainingLetter.setBackground(FrameGui.GREEN_COLOR);
        }
        else {
            RemainingLetter.setText(Character.toString((char)(i+65)));
            RemainingLetter.setBackground(FrameGui.DEFAULT_REMAINING_LETTER_COLOR);
        }
        RemainingLetter.setMargin(new Insets(0, 0, 0, 0));
        
        RemainingLetter.addActionListener(this);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(!FrameGui.ResultPanel.isVisible() && source == RemainingLetter) {
            if(RemainingLettersItem != 26) {
                WordGuesser.frame.keyPressed((char)(RemainingLettersItem+65));
            }
            else if (RemainingLettersItem == 26) {
                WordGuesser.frame.enterPressed();
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
    
    public void updateScreen() {
        int minSize = Math.min(RemainingLetter.getWidth(), RemainingLetter.getHeight());
        RemainingLetter.setFont(RemainingLetter.getFont().deriveFont(Font.BOLD, (minSize+80)/12));
    }

}