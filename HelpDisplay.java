import javax.swing.*;
import java.awt.*;  

public class HelpDisplay {

    static private final Color HELP_PANEL_COLOR = new Color(205, 200, 200);

    public static void createHelpDisplay() {

        GridBagConstraints frameC = new GridBagConstraints();        
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 15;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridy++;
        FrameGui.HelpDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        JLabel Welcome = new JLabel("Welcome to WordGuesser!");
        FrameGui.HelpDisplayPanel.add(Welcome, c);
        c.gridy++;
        FrameGui.HelpDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        JLabel InstructionsHeader = new JLabel("How to Play: ");
        FrameGui.HelpDisplayPanel.add(InstructionsHeader, c);
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
        FrameGui.HelpDisplayPanel.add(Instructions, c);
        c.gridy++;
        JLabel DailyHeader = new JLabel("Daily Game Mode: ");
        FrameGui.HelpDisplayPanel.add(DailyHeader, c);
        c.gridy++;
        JTextArea Daily = new JTextArea(" ");
        Daily.setEditable(false);
        Daily.setBackground(HELP_PANEL_COLOR);
        Daily.setText(
            "- Randomly chosen word every day\n" +
            "- Only one game per day");
        FrameGui.HelpDisplayPanel.add(Daily, c);
        c.gridy++;
        JLabel PracticeHeader = new JLabel("Practice Game Mode: ");
        FrameGui.HelpDisplayPanel.add(PracticeHeader, c);
        c.gridy++;
        JTextArea Practice = new JTextArea(" ");
        Practice.setEditable(false);
        Practice.setBackground(HELP_PANEL_COLOR);
        Practice.setText(
            "- Randomly chosen word every game\n"+
            "- Unlimited games per day");
        FrameGui.HelpDisplayPanel.add(Practice, c);
        c.gridy++;
        JLabel StatsHeader = new JLabel("Stats: ");
        FrameGui.HelpDisplayPanel.add(StatsHeader, c);
        c.gridy++;
        JTextArea Stats = new JTextArea(" ");
        Stats.setEditable(false);
        Stats.setBackground(HELP_PANEL_COLOR);
        Stats.setText(
            "Stats save to the Hard Drive automatically.\n"+
            "Click on the Stats Button on the Home Screen \n"+
            "to view your stats for Daily & Practice mode.");
        FrameGui.HelpDisplayPanel.add(Stats, c);
        c.gridy++;
        FrameGui.HelpDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        JLabel CreditsHeader = new JLabel("Credits: ");
        FrameGui.HelpDisplayPanel.add(CreditsHeader, c);
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
        FrameGui.HelpDisplayPanel.add(Credits, c);
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

        
        FrameGui.HelpDisplayPanel.setBackground(HELP_PANEL_COLOR);

        
        FrameGui.HelpDisplayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        FrameGui.HelpDisplayScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        FrameGui.HelpDisplayScroll.getVerticalScrollBar().setUnitIncrement(8);

        FrameGui.Frame.getContentPane().setLayout(new GridBagLayout());
        frameC.gridy++;
        FrameGui.Frame.add(FrameGui.HelpDisplayScroll, frameC);

        FrameGui.Frame.getContentPane().setLayout(null);
        FrameGui.HelpDisplayScroll.setVisible(false);
    }
    
    public static void openHelpDisplay() {
        FrameGui.HelpDisplayScroll.setVisible(true);
    }
    
    public static void closeHelpDisplay() {
        FrameGui.HelpDisplayScroll.setVisible(false);
    }
    
    public static void updateScreen() {
        int PanelX = FrameGui.width/5;
        int PanelY = FrameGui.height/8;
        int PanelWidth = FrameGui.width-(2*PanelX);  
        int PanelHeight = FrameGui.height-(2*PanelY);
        FrameGui.HelpDisplayScroll.setBounds(PanelX, PanelY, PanelWidth, PanelHeight);
    }
    
}