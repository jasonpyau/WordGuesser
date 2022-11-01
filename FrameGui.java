import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  
import java.util.concurrent.TimeUnit.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FrameGui implements ActionListener {
    
    static FileReader File = new FileReader();
    static private String PracticeWord = "";
    static private String DailyWord = "";
    
    static public JFrame Frame = new JFrame("WordGuesser");
    static private JButton Daily = new JButton("Daily");
    static private JButton Practice = new JButton("Practice");
    static private JButton Stats = new JButton("Stats");
    static private JButton Back = new JButton("Back");
    static private JButton Help = new JButton("Help");
    static private JButton DailyStatsButton = new JButton("D");
    static private JButton PracticeStatsButton = new JButton("P");
    public JButton GuessDisplay = new JButton(" ");
    public JButton RemainingLetter = new JButton(" ");
    static private JTextArea Result = new JTextArea("Result: \nWord: \nAttempts:");
    static private JButton PlayAgain = new JButton("Play Again");
    static public JPanel GuessDisplayPanel = new JPanel();
    static public JPanel RemainingLettersPanel = new JPanel();
    static public JPanel ResultPanel = new JPanel();
    public JButton PlaceHolder = new JButton("");
    static public Toolkit t = Toolkit.getDefaultToolkit();
    static public int width = t.getDefaultToolkit().getScreenSize().width;
    static public int height = t.getDefaultToolkit().getScreenSize().height;
    static public double screenRatio = width/((double)height);
    static public KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();


    static public GuessDisplay[][] GuessDisplays = new GuessDisplay[6][5];
    static public int currentRow;
    static public int currentChar;
    static public int lastChar;
    static GuessDisplay tempG;
    
    static public RemainingLetters[] RemainingLetters = new RemainingLetters[27];
    
    public final Color FRAME_COLOR = new Color(33, 33, 33);
    public final Color DEFAULT_HOME_BUTTON_COLOR = new Color(200, 200, 200);
    public final Color BACK_BUTTON_COLOR = new Color(255, 220, 125);
    public final Color GUESS_PANEL_COLOR = new Color(15, 15, 15);
    public final Color REMAINING_LETTERS_PANEL_COLOR = new Color(15, 15, 15);
    public final Color DEFAULT_GUESS_COLOR = new Color(200, 200, 200);
    public final Color CURRENT_GUESS_COLOR = new Color(191, 220, 220);
    public final Color DEFAULT_REMAINING_LETTER_COLOR = new Color(190, 190, 190);
    public final Color STATS_BUTTON_COLOR = new Color(242, 189, 133);
    public final Color GREEN_COLOR = new Color(139, 188, 152);
    public final Color YELLOW_COLOR = new Color(231, 231, 150);
    public final Color RED_COLOR = new Color(255, 142, 142);
    
    public final int DEFAULT = 0;
    public final int CURRENT = 1;
    public final int GREEN = 2;
    public final int YELLOW = 3;
    public final int RED = 4;
    
    public final int ERROR = -1;
    public final int DAILY_MODE = 0;
    public final int PRACTICE_MODE = 1;
    public final int NEW_GAME = 2;
    static public int gameMode = 0;
    
    JPanel StatDisplayPanel = new JPanel(new GridBagLayout());
    JScrollPane StatDisplayScroll = new JScrollPane(StatDisplayPanel);
    static public StatDisplay DailyStatDisplay;
    static public StatDisplay PracticeStatDisplay;
    
    JPanel HelpDisplayPanel = new JPanel(new GridBagLayout());
    JScrollPane HelpDisplayScroll = new JScrollPane(HelpDisplayPanel);
    static public HelpDisplay HelpDisplay;

    private ImageIcon WordGuesserImage;
    private JLabel WordGuesserLabel = new JLabel();
    
    
    public FrameGui() {
        
        Frame.getContentPane().setBackground(FRAME_COLOR);
        Frame.setSize(width, height);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        Frame.setMinimumSize(new Dimension(900, 420));
        Frame.setAlwaysOnTop(false);
        
        try {
            Frame.setIconImage(ImageIO.read(new File("data/WordGuesserIcon.png")));
        }
        catch (IOException e){}

        Frame.add(WordGuesserLabel);
        Frame.add(Daily);
        Frame.add(Practice);
        Frame.add(Stats);
        Frame.add(Back);
        Frame.add(Help);
        Frame.add(DailyStatsButton);
        Frame.add(PracticeStatsButton);
        
        PlaceHolder.setBackground(new Color(0,0,0));
        Frame.add(GuessDisplayPanel);
        Frame.add(RemainingLettersPanel);
        Frame.add(ResultPanel);
        
        ResultPanel.setVisible(false);
        ResultPanel.add(Result);
        Result.setLineWrap(true);
        Result.setEditable(false);
        ResultPanel.add(PlayAgain);
        PlayAgain.setBackground(YELLOW_COLOR);
        GridLayout ResultPanelLayout = new GridLayout(2,1);
        ResultPanel.setLayout(ResultPanelLayout);

        Frame.add(PlaceHolder);
        
        Daily.setBackground(DEFAULT_HOME_BUTTON_COLOR);
        Practice.setBackground(DEFAULT_HOME_BUTTON_COLOR);
        Stats.setBackground(DEFAULT_HOME_BUTTON_COLOR);
        Help.setBackground(DEFAULT_HOME_BUTTON_COLOR);
        Back.setBackground(BACK_BUTTON_COLOR);
        DailyStatsButton.setBackground(STATS_BUTTON_COLOR);
        PracticeStatsButton.setBackground(STATS_BUTTON_COLOR);
        
        DailyStatsButton.setMargin(new Insets(0, 0, 0, 0));
        PracticeStatsButton.setMargin(new Insets(0, 0, 0, 0));
        
        WordGuesserLabel.setVisible(true);
        Daily.setVisible(true);
        Practice.setVisible(true);
        Stats.setVisible(true);
        Help.setVisible(true);
        Back.setVisible(false);
        DailyStatsButton.setVisible(false);
        PracticeStatsButton.setVisible(false);
        PlaceHolder.setVisible(false);

        GuessDisplayPanel.setSize(600,600);
        GuessDisplayPanel.setBackground(GUESS_PANEL_COLOR);
        GuessDisplayPanel.setVisible(false);
        

        newRemainingLetters();
        GuessDisplayPanel.setSize(200,200);
        RemainingLettersPanel.setBackground(REMAINING_LETTERS_PANEL_COLOR);
        RemainingLettersPanel.setVisible(false);
        GridLayout RemainingLettersPanelLayout = new GridLayout(7,4,5,5);
        RemainingLettersPanel.setLayout(RemainingLettersPanelLayout);
        
        newStatDisplays();
        newHelpDisplay();
        
        Daily.addActionListener(this);
        Practice.addActionListener(this);
        Stats.addActionListener(this);
        Back.addActionListener(this);
        Help.addActionListener(this);
        PlayAgain.addActionListener(this);
        DailyStatsButton.addActionListener(this);
        PracticeStatsButton.addActionListener(this);
        
        newGuessDisplay();
        updateScreen();
        Frame.setVisible(true);
        checkResolutionChange();

    }

    public void actionPerformed(ActionEvent e) {
        Object ButtonClicked = e.getSource();
        if (ButtonClicked == Daily) {
            closeHomeButtons();
            newGameGeneral();
            newDailyGame();
        }
        else if (ButtonClicked == Practice) {
            closeHomeButtons();
            newGameGeneral();
            newPracticeGame();
        }
        else if (ButtonClicked == Stats) {
            closeHomeButtons();
            DailyStatsButton.setVisible(true);
            PracticeStatsButton.setVisible(true);
            DailyStatDisplay.openStatDisplay();
            DailyStatDisplay.setScrollPosition(0);
        }
        else if (ButtonClicked == Help) {
            closeHomeButtons();
            HelpDisplay.openHelpDisplay();
        }
        else if (ButtonClicked == Back) {
            backClicked();
        }
        else if (ButtonClicked == PlayAgain) {
            playAgainClicked();
        }
        else if (ButtonClicked == DailyStatsButton) {
            DailyStatDisplay.openStatDisplay();
            PracticeStatDisplay.closeStatDisplay();
            DailyStatDisplay.setScrollPosition(PracticeStatDisplay.getScrollPosition());
        }
        else if (ButtonClicked == PracticeStatsButton) {
            DailyStatDisplay.closeStatDisplay();
            PracticeStatDisplay.openStatDisplay();
            PracticeStatDisplay.setScrollPosition(DailyStatDisplay.getScrollPosition());
        }
    }
    
    private void closeHomeButtons() {
        WordGuesserLabel.setVisible(false);
        Daily.setVisible(false);
        Practice.setVisible(false);
        Stats.setVisible(false);
        Help.setVisible(false);
        Back.setVisible(true);
    }
    
    private void newGameGeneral() {
        GuessDisplayPanel.setVisible(true);
        RemainingLettersPanel.setVisible(true);
        updateGuessDisplaysVisible(true);
        GuessDisplay.add(PlaceHolder);
        for (int i = 0; i < RemainingLetters.length; i++) {
            RemainingLetters[i].updateScreen();
            setRemainingLetterColor(DEFAULT, (char)(97+i));
        }
    }
    
    private void newDailyGame() {
        ArrayList<String> DailyGame = File.openCurrentGame(DAILY_MODE);
        gameMode = DAILY_MODE;
        if (DailyGame.size() != 0) {
            Daily.setText("Daily");
            Daily.setBackground(DEFAULT_HOME_BUTTON_COLOR);
            if (DailyGame.get(0).equals("True")) {
                DailyWord = DailyGame.get(1);
                loadGame(DailyGame, 2, DailyWord);
            }
            else if (DailyGame.get(0).equals("False")) {
                File.newDailyWord();
                DailyWord = File.getDailyWord();
                File.writeCurrentGames(DAILY_MODE, 0, "", DailyWord);
            }
            else {
                Daily.setText("Daily: Error, wait until tomorrow.");
                Daily.setBackground(RED_COLOR);
                backClicked();
                return;
            }
        }
        else {
            Daily.setText("Daily: Error, wait until tomorrow.");
            Daily.setBackground(RED_COLOR);
            backClicked();
            return;
        }
        if (!ResultPanel.isVisible()) {
            updateGuessDisplayColor(CURRENT);
        }
    }
    
    private void newPracticeGame() {
        ArrayList<String> PracticeGame = File.openCurrentGame(PRACTICE_MODE);
        gameMode = PRACTICE_MODE;
        if (PracticeGame.size() == 2 || PracticeGame.size() == 0) {
            File.newPracticeWord();
            PracticeWord = File.getPracticeWord();
            File.writeCurrentGames(PRACTICE_MODE, 0, "", PracticeWord);
        }
        else if (PracticeGame.size() > 2) {
            PracticeWord = PracticeGame.get(1);
            loadGame(PracticeGame, 2, PracticeWord);
        }
        if (!ResultPanel.isVisible()) {
            updateGuessDisplayColor(CURRENT);
        }
    }
    
    private void loadGame(ArrayList<String> data, int r, String word) {
        String rowAnswer = "";
        StringBuilder s = new StringBuilder();
        for(currentRow = 0; currentRow+2 < data.size() && currentRow < 6; currentRow++) {
            for(currentChar = 0; currentChar < data.get(currentRow+r).length() && currentChar < 5; currentChar++) {
                GuessDisplays[currentRow][currentChar].updateDisplayText(
                    Character.toUpperCase(data.get(currentRow+r).charAt(currentChar)));
                    s.append(data.get(currentRow+r).charAt(currentChar));
            }
            boolean isCorrect = checkAnswer(s.toString(), word);
            if (currentRow == 5 || isCorrect) { 
                showResult(isCorrect);
                return;
            }
            s.setLength(0);
        }
        currentChar = 0;
        updateGuessDisplayColor(CURRENT);

    }
    
    private void backClicked() {
        if(ResultPanel.isVisible()) {
            clearGuessDisplays();
        }
        WordGuesserLabel.setVisible(true);
        Daily.setVisible(true);
        Practice.setVisible(true);
        Stats.setVisible(true);
        Help.setVisible(true);
        HelpDisplay.closeHelpDisplay();
        Back.setVisible(false);
        DailyStatsButton.setVisible(false);
        PracticeStatsButton.setVisible(false);
        GuessDisplayPanel.setVisible(false);
        updateGuessDisplaysVisible(false);
        RemainingLettersPanel.setVisible(false);
        ResultPanel.setVisible(false);
        DailyStatDisplay.closeStatDisplay();
        PracticeStatDisplay.closeStatDisplay();
        for (int i = 97; i < 123; i++) {
            setRemainingLetterColor(DEFAULT, (char)(i));
        }
        clearGuessDisplays();
        currentRow = 0;
        currentChar = 0;
        lastChar = currentChar;
    }
    
    private void playAgainClicked() {
        updateGuessDisplaysVisible(false);
        clearGuessDisplays();
        updateGuessDisplaysVisible(true);
        for (int i = 97; i < 123; i++) {
            setRemainingLetterColor(DEFAULT, (char)(i));
        }
        if(gameMode == PRACTICE_MODE) {
            File.writeCurrentGames(PRACTICE_MODE, 0, "", PracticeWord);
        }
        updateGuessDisplayColor(CURRENT);
        ResultPanel.setVisible(false);
    }

    public void checkResolutionChange() {
        while(true) {
            int previousWidth = Frame.getWidth();
            int previousHeight = Frame.getHeight();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (Frame.getWidth() != previousWidth || Frame.getHeight() != previousHeight) {
                updateScreen();
            }
        }

    }
    
    

    public void updateScreen() {
        width = Frame.getWidth();
        height = Frame.getHeight();
        screenRatio = width/((double)height);

        int homeButtonsX = width/3;
        int homeButtonsWidth = width-(2*homeButtonsX);
        int homeButtonsHeight = height-11*(height/12);

        int WordGuesserImageX = width/4;
        int WordGuesserImageY = 2*height/24;
        int WordGuesserImageWidth = width-(2*WordGuesserImageX);
        int WordGuesserImageHeight = (int)((long)277*WordGuesserImageWidth)/1000;
        if (WordGuesserImageY + WordGuesserImageHeight > 9*height/24) {
            WordGuesserImageHeight = 9*height/24-WordGuesserImageY-height/48;
            WordGuesserImageWidth = (int)(3.6122*WordGuesserImageWidth);
        }

        int backButtonX = width/72;
        int backButtonY = height/72;
        int backButtonWidth = width-(68*backButtonX);
        int backButtonHeight = height-23*(height/24);

        int GuessDisplayPanelX = (screenRatio >= 2.1) ? (int)((screenRatio/((screenRatio+2)/2))*width/3) : width/3;
        int GuessDisplayPanelY = height/20;
        int GuessDisplayPanelWidth =  width-(2*GuessDisplayPanelX);  
        int GuessDisplayPanelHeight = height-(3*GuessDisplayPanelY);
        
        int RemainingLettersPanelX = 1*width/24;
        int RemainingLettersPanelY = 3*height/24;
        int RemainingLettersPanelWidth = 6*width/24;
        int RemainingLettersPanelHeight = height-(2*RemainingLettersPanelY);
        
        int ResultPanelX = 9*width/12;
        int ResultPanelY = 9*height/24;
        int ResultPanelWidth = width/6;
        int ResultPanelHeight = height-(2*ResultPanelY);

        int ResultX = ResultPanelWidth/12;
        int ResultY = ResultPanelHeight/12;
        int ResultlWidth = 10*ResultPanelWidth/12;
        int ResultHeight = 5*ResultPanelHeight/12;
        
        WordGuesserLabel.setBounds(homeButtonsX, WordGuesserImageY, homeButtonsWidth, WordGuesserImageHeight);
        try{
            WordGuesserImage = new ImageIcon(new ImageIcon("data/WordGuesser.png").getImage().getScaledInstance(homeButtonsWidth, WordGuesserImageHeight, Image.SCALE_SMOOTH));
            WordGuesserLabel.setIcon(WordGuesserImage);
        }
        catch (Exception e) {}
        
        Daily.setBounds(homeButtonsX, 9*height/24, homeButtonsWidth, homeButtonsHeight); 
        Practice.setBounds(homeButtonsX, 12*height/24, homeButtonsWidth, homeButtonsHeight);
        Stats.setBounds(homeButtonsX, 15*height/24, homeButtonsWidth, homeButtonsHeight);
        Help.setBounds(homeButtonsX, 18*height/24, homeButtonsWidth, homeButtonsHeight);
        Back.setBounds(backButtonX, backButtonY, backButtonWidth, backButtonHeight);
        GuessDisplayPanel.setBounds(GuessDisplayPanelX, GuessDisplayPanelY, GuessDisplayPanelWidth, GuessDisplayPanelHeight);
        RemainingLettersPanel.setBounds(RemainingLettersPanelX, RemainingLettersPanelY, RemainingLettersPanelWidth, RemainingLettersPanelHeight);
        ResultPanel.setBounds(ResultPanelX, ResultPanelY, ResultPanelWidth, ResultPanelHeight);
        Result.setBounds(ResultX, ResultPanelY, ResultlWidth, ResultHeight);
         
        for (int r = 0; r < GuessDisplays.length; r++) {
            for (int c = 0; c < GuessDisplays[currentRow].length; c++) {
                GuessDisplays[r][c].updateScreen();
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Result.setFont(Result.getFont().deriveFont(Font.BOLD, (ResultPanel.getHeight()+160)/24));
        for (int i = 0; i < RemainingLetters.length; i++) {
            RemainingLetters[i].updateScreen();
        }
        int StatsButtonX = width/5-height/8-height/200;
        int DailyStatsButtonY = 7*height/50;
        int PracticeStatsButtonY = 27*height/100;
        int StatsButtonLength = height/8;
        DailyStatsButton.setBounds(StatsButtonX, DailyStatsButtonY, StatsButtonLength, StatsButtonLength);
        PracticeStatsButton.setBounds(StatsButtonX, PracticeStatsButtonY, StatsButtonLength, StatsButtonLength);
        DailyStatsButton.setFont(DailyStatsButton.getFont().deriveFont(Font.BOLD, (128+StatsButtonLength)/12));
        PracticeStatsButton.setFont(PracticeStatsButton.getFont().deriveFont(Font.BOLD, (128+StatsButtonLength)/12));
        DailyStatDisplay.updateScreen();
        PracticeStatDisplay.updateScreen();
        HelpDisplay.updateScreen();

    }
    
    
    public void newGuessDisplay() {
        for(int r = 0; r < GuessDisplays.length; r++) {
            for(int c = 0; c < GuessDisplays[currentRow].length; c++) {
                GuessDisplays[r][c] = new GuessDisplay(r*5+c);
            }
        }
        tempG = new GuessDisplay(500);
        currentChar = 0;
        currentRow = 0;
        manager.addKeyEventDispatcher(new Dispatcher());


    }
    
    public void newRemainingLetters() {
        for (int i = 0; i < RemainingLetters.length; i++) {
            RemainingLetters[i] = new RemainingLetters(i);
        }
    }
    
    public void newStatDisplays() {
        DailyStatDisplay = new StatDisplay(DAILY_MODE);
        PracticeStatDisplay = new StatDisplay(PRACTICE_MODE);
    }
    
    public void newHelpDisplay() {
        HelpDisplay = new HelpDisplay();
    }
    

    private void updateGuessDisplaysVisible(boolean visible) {
        int tempCurrentRow = currentRow;
        int tempCurrentChar = currentChar;
        for (int r = 0; r < GuessDisplays.length; r++) {
            String rowAnswer = getAnswer(r);
            for (int c = 0; c < GuessDisplays[currentRow].length; c++) {
                GuessDisplays[r][c].GuessDisplayVisible(visible);
            }
            if(!rowAnswer.contains(" ") && r < tempCurrentRow) {
                currentRow = r;
                currentChar = 0;
                if (gameMode == DAILY_MODE) {
                    checkAnswer(rowAnswer, DailyWord);
                }
                else if (gameMode == PRACTICE_MODE) {
                    checkAnswer(rowAnswer, PracticeWord); 
                }
            }
        }
        currentRow = tempCurrentRow;
        currentChar = tempCurrentChar;
        tempG.GuessDisplayVisible(true);
        tempG.GuessDisplayVisible(false);
    }
    
    private void updateGuessDisplayColor(int type) {
        if (type == DEFAULT) {
            GuessDisplays[currentRow][currentChar].updateDisplayColor(DEFAULT_GUESS_COLOR);
        }
        else if (type == CURRENT) {
            GuessDisplays[currentRow][currentChar].updateDisplayColor(CURRENT_GUESS_COLOR);
        }
        else if (type == GREEN) {
            GuessDisplays[currentRow][currentChar].updateDisplayColor(GREEN_COLOR);
        }
        else if (type == YELLOW) {
            GuessDisplays[currentRow][currentChar].updateDisplayColor(YELLOW_COLOR);
        }
        else if (type == RED) {
            GuessDisplays[currentRow][currentChar].updateDisplayColor(RED_COLOR);
        }
        
    }

    private void clearGuessDisplays() {
        for (int r = 0; r < GuessDisplays.length; r++) {
            for (int c = 0; c < GuessDisplays[currentRow].length; c++) {
                GuessDisplays[r][c].updateDisplayText(' ');
            }
        }
    }
    
    private String getAnswer(int row) {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < GuessDisplays[row].length; i++) {
            answer.append(Character.toLowerCase(GuessDisplays[row][i].getDisplayText()));
        }
        return answer.toString();
    }
    
    private boolean checkAnswer(String answer, String word) {
        boolean correct = true;
        HashMap<Integer,Integer> ColorMap = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            if(answer.charAt(i) == word.charAt(i)) {
                ColorMap.put(i, GREEN);
            }
            else if(word.contains(String.valueOf(answer.charAt(i)))) {
                correct = false;
                ColorMap.put(i, YELLOW);
                for (int k = 0; k < i; k++) {
                    if(answer.charAt(k)==answer.charAt(i) && ColorMap.get(k) == RED) {
                        ColorMap.replace(k, YELLOW);
                    }
                }
            }
            else {
                ColorMap.put(i, RED);
                correct = false;
            }
        }
        for(currentChar = 0; currentChar < GuessDisplays[currentRow].length; currentChar++) {
            updateGuessDisplayColor(ColorMap.get(currentChar));
            setRemainingLetterColor(ColorMap.get(currentChar), answer.charAt(currentChar));
        }
        return correct;
    }
    
    private void setRemainingLetterColor(int type, char letter) {
        int RemainingLetterItem = letter-97;
        Color currentColor = RemainingLetters[RemainingLetterItem].getColor();
        
        if (type == DEFAULT) {
            RemainingLetters[RemainingLetterItem].updateRemainingLetterColor(DEFAULT_REMAINING_LETTER_COLOR);
        }
        else if (type == GREEN) {
            RemainingLetters[RemainingLetterItem].updateRemainingLetterColor(GREEN_COLOR);
        }   
        else if (type == YELLOW && !currentColor.equals(GREEN_COLOR)) {
            RemainingLetters[RemainingLetterItem].updateRemainingLetterColor(YELLOW_COLOR);
        }
        else if (type == RED && currentColor.equals(DEFAULT_REMAINING_LETTER_COLOR)) {
            RemainingLetters[RemainingLetterItem].updateRemainingLetterColor(RED_COLOR);
        }
    }
    
    public void showResult(boolean correct) {
        String resultText = (correct) ? "Win" : "Loss";
        String wordText = "";
        if (gameMode == DAILY_MODE) {
            wordText = DailyWord;
        }
        else if (gameMode == PRACTICE_MODE) {
            wordText = PracticeWord;
        }
        Result.setText("  Result: "+resultText+"\n  Word: "+wordText+"\n  Attempts: "+(currentRow+1));
        currentRow = 0;
        lastChar = currentChar;
        currentChar = 0;
        Color setColor = (correct) ? GREEN_COLOR : RED_COLOR;
        ResultPanel.setBackground(setColor);
        Result.setBackground(setColor);
        ResultPanel.setVisible(true);
        if (gameMode == DAILY_MODE) {
            PlayAgain.setVisible(false);
        }
        if (gameMode == PRACTICE_MODE) {
            File.newPracticeWord();
            PracticeWord = File.getPracticeWord();
            PlayAgain.setVisible(true);
        }
    }
    
    public void keyPressed(char letter) {
        if (currentChar <= 4) {
            if (lastChar == 4) {
                return;
            }
            GuessDisplays[currentRow][currentChar].updateDisplayText(Character.toUpperCase(letter));
            updateGuessDisplayColor(DEFAULT);
            lastChar = currentChar;
            currentChar += (currentChar < 4) ? 1 : 0;
            updateGuessDisplayColor(CURRENT);
        }
    }
    
    public void enterPressed() {
        for (int c = 0; c < GuessDisplays[currentRow].length; c++) {
            if (GuessDisplays[currentRow][c].getDisplayText() == ' ') {
                return;
            }
        }
        String answer = getAnswer(currentRow);
        if (!File.isAllowed(answer)) {
            return;
        }
        boolean isCorrect = false;
        if (gameMode == DAILY_MODE) {
            isCorrect = checkAnswer(answer, DailyWord); 
            File.writeCurrentGames(DAILY_MODE, currentRow, answer, "");
        }
        else if (gameMode == PRACTICE_MODE) {
            isCorrect = checkAnswer(answer, PracticeWord); 
            File.writeCurrentGames(PRACTICE_MODE, currentRow, answer, "");
        }
        if (currentRow == 5 || isCorrect) { 
            char charResult = (isCorrect) ? 'W' : 'L';
            if (gameMode == DAILY_MODE) {
                File.writeStats(DAILY_MODE, DailyWord, currentRow+1, charResult);
            }
            else if (gameMode == PRACTICE_MODE) {
                File.writeStats(PRACTICE_MODE, PracticeWord, currentRow+1, charResult);
            }
            showResult(isCorrect);
        }
        else if (currentRow < 5) {
            currentChar = 0;
            lastChar = currentChar;
            currentRow++;
            updateGuessDisplayColor(CURRENT);
        }
    }
    
    
    

   //Checks for Key Press
   private class Dispatcher implements KeyEventDispatcher {
      @Override
       public boolean dispatchKeyEvent(KeyEvent e) {
            if(e.getID() == KeyEvent.KEY_RELEASED && GuessDisplayPanel.isVisible() && !ResultPanel.isVisible()) {
                if(e.getKeyCode() >= 65 && e.getKeyCode() <= 90) { //LETTER 
                    keyPressed(e.getKeyChar());
                }
                else if (e.getKeyCode() == 8) { //BACKSPACE 
                    if (currentChar > 0 && GuessDisplays[currentRow][currentChar].getDisplayText() == ' ') {
                        updateGuessDisplayColor(DEFAULT);
                        GuessDisplays[currentRow][currentChar-1].updateDisplayText(' ');
                        currentChar--;
                        lastChar = currentChar;
                        updateGuessDisplayColor(CURRENT);
                    }
                    else if (GuessDisplays[currentRow][currentChar].getDisplayText() != ' ') {
                        GuessDisplays[currentRow][currentChar].updateDisplayText(' ');
                        lastChar--;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) { //LEFT ARROW
                    if (currentChar > 0) {
                        updateGuessDisplayColor(DEFAULT);
                        currentChar--;
                        lastChar = currentChar;
                        updateGuessDisplayColor(CURRENT);
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { //RIGHT ARROW
                    if (currentChar < 4) {
                        updateGuessDisplayColor(DEFAULT);
                        lastChar = currentChar;
                        currentChar++;
                        updateGuessDisplayColor(CURRENT);
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) { //ENTER KEY
                    enterPressed();
                }
                
            }
           return false;
        }
    }
    

}