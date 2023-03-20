import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  

public class GuessDisplay implements ActionListener {

    int GuessDisplayItem;

    public JButton GuessDisplay = new JButton(" ");
    
    public GuessDisplay(int i) {
        GuessDisplayItem = i;
        
        FrameGui.GuessDisplayPanel.add(GuessDisplay, FrameGui.GuessDisplayPanelGBC);        
        GuessDisplay.setVisible(false);
        updateScreen();
        GuessDisplay.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(!FrameGui.ResultPanel.isVisible() && FrameGui.currentRow == GuessDisplayItem/5 && source == GuessDisplay) {
            FrameGui.defaultRowGuessDisplayColor();
            updateDisplayColor(FrameGui.CURRENT_GUESS_COLOR);
            FrameGui.currentChar = GuessDisplayItem%5;
            FrameGui.lastChar = (FrameGui.currentChar == 4) ? FrameGui.currentChar-1 : FrameGui.currentChar;
        }


    }

    public void GuessDisplayVisible(boolean visible) {
        if (visible) {
            GuessDisplay.setVisible(true);
            updateDisplayColor(FrameGui.DEFAULT_GUESS_COLOR);

        }
        else {
            GuessDisplay.setVisible(false);
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

    public void updateScreen() {
    
        int PanelWidth = FrameGui.GuessDisplayPanel.getWidth(); 
        int GuessDisplayLength = (int)(24*(PanelWidth/125.0));
        int fontSize = (GuessDisplayLength+88)/12;
        GuessDisplay.setFont(GuessDisplay.getFont().deriveFont(Font.BOLD, fontSize));
        GuessDisplay.setPreferredSize(new Dimension(GuessDisplayLength, GuessDisplayLength));
    }

}