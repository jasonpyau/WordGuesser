import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  
import java.util.concurrent.TimeUnit.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.table.*;


public class StatDisplay extends FrameGui {
    private int StatDisplayType;
    
    private JLabel Title = new JLabel(" ");
    private JLabel GamesPlayed = new JLabel("Games Played: ");
    private JLabel CurrentStreak = new JLabel("Current Streak: ");
    private JLabel MaxStreak = new JLabel("Max Streak: ");
    private JLabel WinPercentHeader = new JLabel("Win Percent ");
    private JProgressBar WinPercent = new JProgressBar();
    private JLabel GuessDistributionHeader = new JLabel("Guess Distribution ");
    private JLabel[] GuessDistributionLabels = new JLabel[6];
    private JProgressBar[] GuessDistributions = new JProgressBar[6];
    private JLabel HistoryHeader = new JLabel("History ");
    private DefaultTableModel HistoryModel = new DefaultTableModel();
    private JTable History = new JTable(HistoryModel);
    static private String data[][];
    private String StringType;
    
    private final int DAILY_MODE = 0;
    private final int PRACTICE_MODE = 1;
    private final Color STATS_PANEL_COLOR = new Color(205, 200, 200);
    private final Color HISTORY_COLOR_WIN = new Color(180, 245, 177);
    private final Color HISTORY_COLOR_LOSS = new Color(241, 176, 176);
    
    public StatDisplay(int type) {

        StatDisplayType = type;
        if (type == DAILY_MODE) {
            StringType = "Daily";
        }
        else {
            StringType = "Practice";
        }
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridy++;
        c.ipady = 15;
        StatDisplayPanel.add(Title, c);
        c.gridy++;
        StatDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        c.ipady = 20;
        StatDisplayPanel.add(GamesPlayed, c);
        c.gridy++;
        StatDisplayPanel.add(CurrentStreak, c);
        c.gridy++;
        StatDisplayPanel.add(MaxStreak, c);
        c.ipady = 15;
        c.gridy++;
        StatDisplayPanel.add(new JLabel(" "), c);        
        c.gridy++;
        StatDisplayPanel.add(WinPercentHeader, c);
        c.gridy++;
        c.ipady = 10;
        StatDisplayPanel.add(WinPercent, c);
        WinPercent.setValue(0);
        WinPercent.setStringPainted(true); 
        c.ipady = 15;
        c.gridy++;
        StatDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        StatDisplayPanel.add(GuessDistributionHeader, c);
        c.gridy++;
        
        newGuessDistributions();
        c.ipady = 10;
        c.gridwidth = GridBagConstraints.RELATIVE;
        for(int i = 0; i < GuessDistributionLabels.length; i++) {
            StatDisplayPanel.add(GuessDistributionLabels[i], c);
            GuessDistributionLabels[i].setFont(GuessDistributionLabels[i].getFont().deriveFont(Font.BOLD, 16));
            GuessDistributionLabels[i].setText((i+1)+": ");
        
            StatDisplayPanel.add(GuessDistributions[i], c);
            GuessDistributions[i].setValue(0);
            GuessDistributions[i].setStringPainted(true);  
            
            c.gridy++;
        }
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.ipady = 15;
        StatDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        StatDisplayPanel.add(HistoryHeader, c);
        c.gridy++;
        StatDisplayPanel.add(History, c);

        Title.setFont(Title.getFont().deriveFont(Font.BOLD, 18));
        Title.setText(StringType+" Mode Statistics");
        GamesPlayed.setFont(GamesPlayed.getFont().deriveFont(Font.BOLD, 14));
        CurrentStreak.setFont(CurrentStreak.getFont().deriveFont(Font.BOLD, 14));
        MaxStreak.setFont(MaxStreak.getFont().deriveFont(Font.BOLD, 14));
        WinPercentHeader.setFont(WinPercentHeader.getFont().deriveFont(Font.BOLD, 16));
        GuessDistributionHeader.setFont(GuessDistributionHeader.getFont().deriveFont(Font.BOLD, 16));
        HistoryHeader.setFont(HistoryHeader.getFont().deriveFont(Font.BOLD, 16));
        
        StatDisplayPanel.setSize(200,200);
        StatDisplayPanel.setBackground(STATS_PANEL_COLOR);

        
        StatDisplayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        StatDisplayScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        StatDisplayScroll.getVerticalScrollBar().setUnitIncrement(8);
        StatDisplayScroll.setSize(200,200);
        
        
        Frame.add(StatDisplayScroll);

        StatDisplayScroll.setVisible(false);

    }
    
    public void openStatDisplay() {
        HistoryModel.setRowCount(0);
        HistoryModel.setColumnCount(0);
        StatDisplayScroll.setVisible(true);
        data = File.openStats(StatDisplayType);
        int numGames = File.getNumGames();
        GamesPlayed.setText("Games Played: "+numGames);
        CurrentStreak.setText("Current Streak: "+File.getCurrentStreak());
        MaxStreak.setText("Max Streak: "+File.getMaxStreak());
        WinPercent.setValue((int)Math.round((double)File.getNumWins()/numGames*100));
        for(int i = 0; i < GuessDistributions.length; i++) {
            GuessDistributions[i].setValue((int)Math.round((double)File.getAttemptsNum(i+1)/numGames*100));
        }
        History.setEnabled(false);
        String column[] = {"#","Word", "Attempts", "Result", "Date", "Time"};
        HistoryModel.addColumn(column[0]);
        HistoryModel.addColumn(column[1]);
        HistoryModel.addColumn(column[2]);
        HistoryModel.addColumn(column[3]);
        HistoryModel.addColumn(column[4]);
        HistoryModel.addColumn(column[5]);
        HistoryModel.addRow(column);
        for(int i = data.length-1; i >= 0; i--) {
            String numElement = String.valueOf((Integer.valueOf(data[i][4])+1));
            String resultText = (data[i][3].equals("W")) ? "Win" : "Loss";
            HistoryModel.addRow(
            new String[] {
                numElement,
                data[i][1],
                data[i][2],
                resultText,
                data[i][0].substring(0,10),
                data[i][0].substring(10)
                });
            History.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row!=0) {
                        c.setBackground(table.getValueAt(row, 3).equals("Win") ? HISTORY_COLOR_WIN : HISTORY_COLOR_LOSS);
                    }
                    return c;
                }
            });
        }

        
    }
    
    public void closeStatDisplay() {
        StatDisplayScroll.setVisible(false);
        HistoryModel.setRowCount(0);
        HistoryModel.setColumnCount(0);
    }
    
    private void newGuessDistributions() {
        for(int i = 0; i < GuessDistributionLabels.length; i++){
            GuessDistributionLabels[i] = new JLabel();
        }
        for(int i = 0; i < GuessDistributions.length; i++){
            GuessDistributions[i] = new JProgressBar();
        }
    }
    
    public int getScrollPosition() {
        return StatDisplayScroll.getVerticalScrollBar().getValue();
    }
    
    public void setScrollPosition(int scrollPosition) {
        StatDisplayScroll.getVerticalScrollBar().setValue(scrollPosition);
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
        StatDisplayScroll.setBounds(PanelX, PanelY, PanelWidth, PanelHeight);
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