import javax.swing.*;
import java.awt.*;  
import javax.swing.table.*;


public class StatDisplay {
    private int StatDisplayType;
    
    static private JLabel Title = new JLabel(" ");
    static private JLabel GamesPlayed = new JLabel("Games Played: ");
    static private JLabel CurrentStreak = new JLabel("Current Streak: ");
    static private JLabel MaxStreak = new JLabel("Max Streak: ");
    static private JLabel WinPercentHeader = new JLabel("Win Percent ");
    static private JProgressBar WinPercent = new JProgressBar();
    static private JLabel GuessDistributionHeader = new JLabel("Guess Distribution ");
    static private JLabel[] GuessDistributionLabels = new JLabel[6];
    static private JProgressBar[] GuessDistributions = new JProgressBar[6];
    static private JLabel HistoryHeader = new JLabel("History ");
    static private DefaultTableModel HistoryModel = new DefaultTableModel();
    static private JTable History = new JTable(HistoryModel);
    static private String data[][];
    private String StringType;
    
    static private final int DAILY_MODE = FrameGui.DAILY_MODE;
    static private final int PRACTICE_MODE = FrameGui.PRACTICE_MODE;
    static private final Color STATS_PANEL_COLOR = new Color(205, 200, 200);
    static private final Color HISTORY_COLOR_WIN = new Color(180, 245, 177);
    static private final Color HISTORY_COLOR_LOSS = new Color(241, 176, 176);
    
    public StatDisplay(int type) {
        StatDisplayType = type;
        if (type == DAILY_MODE) {
            StringType = "Daily";
        }
        else {
            StringType = "Practice";
        }
    }

    public static void createStatPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridy++;
        c.ipady = 15;
        FrameGui.StatDisplayPanel.add(Title, c);
        c.gridy++;
        FrameGui.StatDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        c.ipady = 20;
        FrameGui.StatDisplayPanel.add(GamesPlayed, c);
        c.gridy++;
        FrameGui.StatDisplayPanel.add(CurrentStreak, c);
        c.gridy++;
        FrameGui.StatDisplayPanel.add(MaxStreak, c);
        c.ipady = 15;
        c.gridy++;
        FrameGui.StatDisplayPanel.add(new JLabel(" "), c);        
        c.gridy++;
        FrameGui.StatDisplayPanel.add(WinPercentHeader, c);
        c.gridy++;
        c.ipady = 10;
        FrameGui.StatDisplayPanel.add(WinPercent, c);
        WinPercent.setValue(0);
        WinPercent.setStringPainted(true); 
        c.ipady = 15;
        c.gridy++;
        FrameGui.StatDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        FrameGui.StatDisplayPanel.add(GuessDistributionHeader, c);
        c.gridy++;
        
        newGuessDistributions();
        c.ipady = 10;
        c.gridwidth = GridBagConstraints.RELATIVE;
        for(int i = 0; i < GuessDistributionLabels.length; i++) {
            FrameGui.StatDisplayPanel.add(GuessDistributionLabels[i], c);
            GuessDistributionLabels[i].setFont(GuessDistributionLabels[i].getFont().deriveFont(Font.BOLD, 16));
            GuessDistributionLabels[i].setText((i+1)+": ");
        
            FrameGui.StatDisplayPanel.add(GuessDistributions[i], c);
            GuessDistributions[i].setValue(0);
            GuessDistributions[i].setStringPainted(true);  
            
            c.gridy++;
        }
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.ipady = 15;
        FrameGui.StatDisplayPanel.add(new JLabel(" "), c);
        c.gridy++;
        FrameGui.StatDisplayPanel.add(HistoryHeader, c);
        c.gridy++;
        FrameGui.StatDisplayPanel.add(History, c);

        Title.setFont(Title.getFont().deriveFont(Font.BOLD, 18));
        GamesPlayed.setFont(GamesPlayed.getFont().deriveFont(Font.BOLD, 14));
        CurrentStreak.setFont(CurrentStreak.getFont().deriveFont(Font.BOLD, 14));
        MaxStreak.setFont(MaxStreak.getFont().deriveFont(Font.BOLD, 14));
        WinPercentHeader.setFont(WinPercentHeader.getFont().deriveFont(Font.BOLD, 16));
        GuessDistributionHeader.setFont(GuessDistributionHeader.getFont().deriveFont(Font.BOLD, 16));
        HistoryHeader.setFont(HistoryHeader.getFont().deriveFont(Font.BOLD, 16));
        
        FrameGui.StatDisplayPanel.setBackground(STATS_PANEL_COLOR);

        
        FrameGui.StatDisplayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        FrameGui.StatDisplayScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        FrameGui.StatDisplayScroll.getVerticalScrollBar().setUnitIncrement(8);
        FrameGui.Frame.add(FrameGui.StatDisplayScroll);

        FrameGui.StatDisplayScroll.setVisible(false);
    }
    
    public void openStatDisplay() {
        Title.setText(StringType+" Mode Statistics");
        HistoryModel.setRowCount(0);
        HistoryModel.setColumnCount(0);
        FrameGui.StatDisplayScroll.setVisible(true);
        data = FrameGui.File.openStats(StatDisplayType);
        int numGames = FrameGui.File.getNumGames();
        GamesPlayed.setText("Games Played: "+numGames);
        CurrentStreak.setText("Current Streak: "+FrameGui.File.getCurrentStreak());
        MaxStreak.setText("Max Streak: "+FrameGui.File.getMaxStreak());
        WinPercent.setValue((int)Math.round((double)FrameGui.File.getNumWins()/numGames*100));
        for(int i = 0; i < GuessDistributions.length; i++) {
            GuessDistributions[i].setValue((int)Math.round((double)FrameGui.File.getAttemptsNum(i+1)/numGames*100));
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
        if (data == null)
            return;
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
    
    public static void closeStatDisplay() {
        FrameGui.StatDisplayScroll.setVisible(false);
        HistoryModel.setRowCount(0);
        HistoryModel.setColumnCount(0);
    }
    
    private static void newGuessDistributions() {
        for(int i = 0; i < GuessDistributionLabels.length; i++){
            GuessDistributionLabels[i] = new JLabel();
        }
        for(int i = 0; i < GuessDistributions.length; i++){
            GuessDistributions[i] = new JProgressBar();
        }
    }
    
    public static int getScrollPosition() {
        return FrameGui.StatDisplayScroll.getVerticalScrollBar().getValue();
    }
    
    public static void setScrollPosition(int scrollPosition) {
        FrameGui.StatDisplayScroll.getVerticalScrollBar().setValue(scrollPosition);
    }
    
    public static void updateScreen() {
        int PanelX = FrameGui.width/5;
        int PanelY = FrameGui.height/8;
        int PanelWidth =  FrameGui.width-(2*PanelX);  
        int PanelHeight = FrameGui.height-(2*PanelY);
        FrameGui.StatDisplayScroll.setBounds(PanelX, PanelY, PanelWidth, PanelHeight);
    }

}