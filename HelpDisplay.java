import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  
import java.util.concurrent.TimeUnit.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;


public class HelpDisplay extends FrameGui {
    
    private final Color HELP_PANEL_COLOR = new Color(205, 200, 200);
    public HelpDisplay() {
        
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 15;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridy++;
        HelpDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        JLabel Welcome = new JLabel("Welcome to WordGuesser!");
        HelpDisplayPanel.add(Welcome, c);
        c.gridy++;
        HelpDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        JLabel InstructionsHeader = new JLabel("How to Play: ");
        HelpDisplayPanel.add(InstructionsHeader, c);
        c.gridy++;
        JTextArea Instructions = new JTextArea(" ");
        Instructions.setEditable(false);
        Instructions.setBackground(HELP_PANEL_COLOR);
        Instructions.setText(
            "A 5-letter word is randomly chosen and you have \n"+
            "6 attempts to guess the correct word. \n\n"+
            "Each guess must be a valid 5-letter word. \n"+
            "The color for each letter will change based on \n"+
            "the letter's correctness. \n\n"+
            "- If the letter is green, the letter is in the word and \n"+
            "is in the correct spot. \n"+
            "- If the letter is yellow, the letter is in the word, but \n"+
            "the word is not in the correct spot.\n"+
            "- If the letter is red, the letter is not in the word.\n");
        HelpDisplayPanel.add(Instructions, c);
        c.gridy++;
        JLabel DailyHeader = new JLabel("Daily Game Mode: ");
        HelpDisplayPanel.add(DailyHeader, c);
        c.gridy++;
        JTextArea Daily = new JTextArea(" ");
        Daily.setEditable(false);
        Daily.setBackground(HELP_PANEL_COLOR);
        Daily.setText(
            "- Randomly chosen word every day\n" +
            "- Only one game per day");
        HelpDisplayPanel.add(Daily, c);
        c.gridy++;
        JLabel PracticeHeader = new JLabel("Practice Game Mode: ");
        HelpDisplayPanel.add(PracticeHeader, c);
        c.gridy++;
        JTextArea Practice = new JTextArea(" ");
        Practice.setEditable(false);
        Practice.setBackground(HELP_PANEL_COLOR);
        Practice.setText(
            "- Randomly chosen word every game\n"+
            "- Unlimited games per day");
        HelpDisplayPanel.add(Practice, c);
        c.gridy++;
        JLabel StatsHeader = new JLabel("Stats: ");
        HelpDisplayPanel.add(StatsHeader, c);
        c.gridy++;
        JTextArea Stats = new JTextArea(" ");
        Stats.setEditable(false);
        Stats.setBackground(HELP_PANEL_COLOR);
        Stats.setText(
            "Stats save to the Hard Drive automatically.\n"+
            "Click on the Stats Button on the Home Screen \n"+
            "to view your stats for Daily & Practice mode.");
        HelpDisplayPanel.add(Stats, c);
        c.gridy++;
        HelpDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        JLabel CreditsHeader = new JLabel("Credits: ");
        HelpDisplayPanel.add(CreditsHeader, c);
        c.gridy++;
        JTextArea Credits = new JTextArea(" ");
        Credits.setEditable(false);
        Credits.setBackground(HELP_PANEL_COLOR);
        Credits.setText(
            "Based on the popular game Wordle by New York Times.\n\n"+
            "Programmed by: Jason Yau \n"+
            "Developed by: Jason Yau \n\n"+
            "View Jason's GitHub for more projects:\n"+
            "https://github.com/jasonpyau");
        HelpDisplayPanel.add(Credits, c);
        c.gridy++;



        Welcome.setFont(Welcome.getFont().deriveFont(Font.BOLD, 18));
        InstructionsHeader.setFont(InstructionsHeader.getFont().deriveFont(Font.BOLD, 16));
        Instructions.setFont(Instructions.getFont().deriveFont(Font.BOLD, 14));
        DailyHeader.setFont(DailyHeader.getFont().deriveFont(Font.BOLD, 16));
        Daily.setFont(Daily.getFont().deriveFont(Font.BOLD, 14));
        PracticeHeader.setFont(PracticeHeader.getFont().deriveFont(Font.BOLD, 16));
        Practice.setFont(Practice.getFont().deriveFont(Font.BOLD, 14));
        StatsHeader.setFont(StatsHeader.getFont().deriveFont(Font.BOLD, 16));
        Stats.setFont(Stats.getFont().deriveFont(Font.BOLD, 14));
        CreditsHeader.setFont(CreditsHeader.getFont().deriveFont(Font.BOLD, 16));
        Credits.setFont(Credits.getFont().deriveFont(Font.BOLD, 14));

        
        HelpDisplayPanel.setSize(200,200);
        HelpDisplayPanel.setBackground(HELP_PANEL_COLOR);
        
        HelpDisplayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        HelpDisplayScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        HelpDisplayScroll.getVerticalScrollBar().setUnitIncrement(8);
        HelpDisplayScroll.setSize(200,200);
        Frame.add(HelpDisplayScroll);

        HelpDisplayScroll.setVisible(false);
    }
    
    public void openHelpDisplay() {
        HelpDisplayScroll.setVisible(true);
    }
    
    public void closeHelpDisplay() {
        HelpDisplayScroll.setVisible(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    @Override
    public void updateScreen() {
        int PanelX = width/5;
        int PanelY = height/8;
        int PanelWidth =  width-(2*PanelX);  
        int PanelHeight = height-(2*PanelY);
        HelpDisplayScroll.setBounds(PanelX, PanelY, PanelWidth, PanelHeight);
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