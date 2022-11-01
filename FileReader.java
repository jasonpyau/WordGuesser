import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  
import java.util.concurrent.TimeUnit.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.text.SimpleDateFormat;
import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FileReader {
    static private ArrayList<String> AllowedWords = new ArrayList<>();
    static private ArrayList<String> PossibleWords = new ArrayList<>();
    static private HashMap<Integer, Integer> AttemptsMap = new HashMap<>();
    static private String DailyWord = "crane";
    static private String PracticeWord = "crane";
    
    private long unixTime = System.currentTimeMillis();
    private SimpleDateFormat DateFormat;    
    private Date date;
    private long MonthDayYear;
    
    public final int ERROR = -1;
    public final int DAILY_MODE = 0;
    public final int PRACTICE_MODE = 1;
    
    static private int currentStreak = 0;
    static private int maxStreak = 0;
    static private int numWins = 0;

    public FileReader() {
        newMonthDayYear();
        createAllowedWords();
        createPossibleWords();
        newDailyWord();
        newPracticeWord();
        newAttemptsMap();
    }

    public void createAllowedWords() {
        Scanner scan;
        try {
            scan = new Scanner(new File("data/AllowedWords.txt"));
        } 
        catch (FileNotFoundException e) {
            return; 
        }
        String FileLine;
        while (scan.hasNext()) {
            FileLine = scan.nextLine();
            AllowedWords.add(FileLine);
        }
        scan.close();
    }

    public void createPossibleWords() {
        Scanner scan;
        try {
            scan = new Scanner(new File("data/PossibleWords.txt"));
        } 
        catch (FileNotFoundException e) {
            return; 
        }
        String FileLine;
        while (scan.hasNext()) {
            FileLine = scan.nextLine();
            PossibleWords.add(FileLine);
        }
        scan.close();
    }

    public String getDailyWord() {
        return DailyWord;
    }

    public String getPracticeWord() {
        return PracticeWord;
    }

    public boolean isAllowed(String word) {
        int min = 0;
        int i = 0;
        int max = AllowedWords.size()-1;
        
        while (max >= min) {
            i = (max+min)/2;
            String AllowedWord = AllowedWords.get(i);
            if (AllowedWord.compareTo(word) == 0) {
                return true;
            }
            else if (AllowedWord.compareTo(word) > 0) {
                max = i - 1;
            }
            else if (AllowedWord.compareTo(word) < 0) {
                min = i + 1;
            }
        }
        return false;
    }
    
    private void newMonthDayYear() {
        unixTime = System.currentTimeMillis();
        DateFormat = new SimpleDateFormat("MMddyyyy");
        date = new Date(unixTime);
        MonthDayYear = Long.valueOf(DateFormat.format(date));
        
    }
    
    public void newPracticeWord() {
        unixTime = System.currentTimeMillis();
        Random rand = new Random(unixTime);
        PracticeWord = PossibleWords.get(Math.abs(rand.nextInt())%PossibleWords.size());
    }
    
    public void newDailyWord() {
        newMonthDayYear();
        Random rand = new Random(MonthDayYear);
        DailyWord = PossibleWords.get(Math.abs(rand.nextInt())%PossibleWords.size());
    }
    
    private String e(String s) {
        String e = "";
        try {
            Key k = new SecretKeySpec(s().getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, k);
            e = new String(Base64.getEncoder().encode(cipher.doFinal(s.getBytes())));
        }
        catch (Exception ex) {}
        return e;
    }
    
    private String d(String s) {
        String d = "";
        try {
            Key k = new SecretKeySpec(s().getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, k);
            byte[] b = cipher.doFinal(Base64.getDecoder().decode(s));
            d = new String(b);
        }
        catch (Exception ex) {}
        return d;
    }

    public void writeCurrentGames(int type, int attemptNum, String updateString, String word) {
        try {
            File file = new File("data/CurrentGames.txt");
            if (type == ERROR || file.createNewFile()) {
                FileWriter fw = new FileWriter(file);
                fw.write(dailyError()+"\n");
                fw.append(newCurrentGame(getPracticeWord()));
                fw.flush();
                fw.close();
                return;
            }
            Scanner scan = new Scanner(file);
            String dData = scan.nextLine(), pData = scan.nextLine();
            FileWriter fw = new FileWriter(file);
            
            if (updateString.isEmpty()) {
                if (type == DAILY_MODE) {
                    fw.append(newCurrentGame(word)+"\n");
                    fw.append(pData);
                }
                else if (type == PRACTICE_MODE) {
                    fw.append(dData+"\n");
                    fw.append(newCurrentGame(word));
                }
            }
            else {
                if (type == DAILY_MODE) {
                    fw.append(updateCurrentGame(dData, attemptNum, updateString)+"\n");
                    fw.append(pData);
                }
                else if (type == PRACTICE_MODE) {
                    fw.append(dData+"\n");
                    fw.append(updateCurrentGame(pData, attemptNum, updateString));
                }
            }
            
            fw.flush();
            fw.close();
            scan.close();
        }
        catch (Exception e) {
            
        }
        
    }
    
    private String dailyError() {
        newMonthDayYear();
        return e(MonthDayYear+"*crane"+d("btY3kBR5C/LwF2dB6T1AcDPHtDyyj3Gh0ZZeUiDxF+nCdLWQxWY3Y9pZOfczHT0i"));
    }
    
    private String newCurrentGame(String word) {
        newMonthDayYear();
        return e(MonthDayYear+"*"+word+d("YAY/OBiWAFekj4mVBO9pCOkwQz3NxQU7YR1sNWuj6t8WneycsH4Gcz6riXmorUbf"));
    }
    
    public String updateCurrentGame(String data, int attemptNum, String updateString) {
        data = d(data);
        int item = 0, i = 0;
        String temp;
        for (; i < data.length(); i++) {
            if (data.charAt(i) == '*') {
                item++;
            }
            if (item-2 == attemptNum) {
                break;
            }
        }
        i++;
        StringBuilder s = new StringBuilder(data);
        for (int k = 0; i < data.length() && k < updateString.length(); i++, k++) {
            if(data.charAt(i) == '*') {
                break;
            }
            s.setCharAt(i, updateString.charAt(k));
        }
        return e(s.toString());
    }
    
    private String s() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < 16; i++) {
            s.append(PossibleWords.get(i*144).charAt(i%3+1));
        }
        return s.toString();
    }
    
    public ArrayList<String> openCurrentGame(int type) {
        ArrayList<String> CurrentGame = new ArrayList<>();
        String data = "";
        try {
            Scanner scan = new Scanner(new File("data/CurrentGames.txt"));
            if (type == DAILY_MODE) {
                data = d(scan.nextLine());
            }
            else if (type == PRACTICE_MODE) {
                scan.nextLine();
                data = d(scan.nextLine());
            }
            scan.close();
        }
        catch (Exception e) {
            writeCurrentGames(ERROR, 6, "", "");
        }
        int item = 0, i = 0;
        StringBuilder s = new StringBuilder();
        try {
            for(i = 0; i < data.length(); i++) {
                if (data.charAt(i) == '*') {
                    if (item == 0) {
                        long fileMonthDayYear = Long.valueOf(String.valueOf(s));
                        newMonthDayYear();
                        if (MonthDayYear == fileMonthDayYear) {
                            CurrentGame.add("True");
                        }
                        else {
                            CurrentGame.add("False");
                        }
                    }
                    else if (item == 1) {
                        if (isAllowed(s.toString())) {
                            CurrentGame.add(s.toString());
                        }
                        else {
                            break;
                        }
                    }
                    else if (s.toString().equals("empty")) {
                        item = 8;
                        i++;
                        break;
                    }
                    else {
                        if (isAllowed(s.toString())) {
                            CurrentGame.add(s.toString());
                        }
                        else {
                            break;
                        }
                    }
                    s.setLength(0);
                    item++;
                }
                else {
                    s.append(data.charAt(i));
                }
            }
        }
        catch (Exception e) {
            writeCurrentGames(ERROR, 6, "", "");
            return new ArrayList<>();
        }
        if(item != 8 || data.charAt(i-1) != '*') {
            writeCurrentGames(ERROR, 6, "", "");
            return new ArrayList<>();
        }
        return CurrentGame;
    }
    
    private String newYearMonthDayTime() {
        unixTime = System.currentTimeMillis();
        SimpleDateFormat YMDTFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date YMDTdate = new Date(unixTime);
        return YMDTFormat.format(YMDTdate);
    }
    
    private String statsTime(String time) {
        Date YMDTdate;
        try {
            SimpleDateFormat YMDTFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            YMDTdate = YMDTFormat.parse(time);
        }
        catch (Exception e) {
            return "";
        }
        SimpleDateFormat statsFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        return statsFormat.format(YMDTdate);
    }
    
    public void writeStats(int type, String word, int numAttempts, char result) {
        File file;
        File file2 = new File("data/num.txt");
        if (type == DAILY_MODE) {
            file = new File("data/DailyStats.txt");
        }
        else if (type == PRACTICE_MODE) {
            file = new File("data/PracticeStats.txt");
        }
        else {
            return;
        }
        try {
            file.createNewFile();
            if (file2.createNewFile()) {
                FileWriter fw = new FileWriter(file2);
                fw.append(e("0")+"\n");
                fw.append(e("0"));
                fw.flush();
                fw.close();
            }
        }
        catch (Exception e) {}

        int num = 0;
        try {
            if (word.equals("ERR0R")) {
                num = numAttempts;
                throw new Exception("Error");
            }
            StringBuilder dataBuilder = new StringBuilder();
            dataBuilder.append(newYearMonthDayTime()+"*");
            dataBuilder.append(word+"*");
            dataBuilder.append(numAttempts+"*");
            dataBuilder.append(result+"*");
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                num++;
                scan.nextLine();
            }
            dataBuilder.append(num+"*");
            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(e(dataBuilder.toString()));
            fw.flush();
            fw.close();
            scan.close();
        }
        catch (Exception e) {}
        try {
            Scanner scan = new Scanner(file2);
            String dData = scan.nextLine(), pData = scan.nextLine();
            FileWriter fw = new FileWriter(file2);
            if (type == DAILY_MODE) {
                fw.append(e(String.valueOf(num))+"\n");
                fw.append(pData);
            }
            else if (type == PRACTICE_MODE) {
                fw.append(dData+"\n");
                fw.append(e(String.valueOf(num)));
            }
            fw.flush();
            fw.close();
            scan.close();
        }
        catch (Exception e) {
            try {
                FileWriter fw = new FileWriter(file2);
                if (type == DAILY_MODE) {
                    fw.append(e(String.valueOf(num))+"\n");
                    fw.append(e("0"));
                }
                else if (type == PRACTICE_MODE) {
                    fw.append(e("0")+"\n");
                    fw.append(e(String.valueOf(num)));
                }
                fw.flush();
                fw.close();
            }
            catch (Exception ex) {}
        }
    }
    
    public String[][] openStats(int type) {
        int rows = 0;
        File file;
        try {
            Scanner scan = new Scanner(new File("data/num.txt"));
            if (type == DAILY_MODE) {
                rows = Integer.valueOf(d(scan.nextLine()))+1;
                file = new File("data/DailyStats.txt");
            }
            else {
                scan.nextLine();
                rows = Integer.valueOf(d(scan.nextLine()))+1;
                file = new File("data/PracticeStats.txt");
            }
            scan.close();
        }
        catch (Exception e) {
            return new String[0][0];
        }
        String stats[][] = new String[rows][5];
        int r = 0;
        try {
            Scanner scan = new Scanner(file);
            newAttemptsMap();
            newStreaks();
            newNumWins();
            int last = 0;
            while (r < rows) {
                String data = d(scan.nextLine());
                int item = 0;
                int attempts = 6;
                char result = 'L';
                boolean valid = false;
                StringBuilder dataBuilder = new StringBuilder();
                int c = 0;
                for(; c < data.length(); c++) {
                    if (data.charAt(c) == '*') {
                        String dataElement = dataBuilder.toString();
                        switch (item) {
                            case 0:
                                dataElement = statsTime(dataElement);
                                valid = true;
                                break;
                            case 1:
                                if (isAllowed(dataElement)) {
                                    valid = true;
                                }
                                break;
                            case 2:
                                attempts = Integer.parseInt(dataElement);
                                if (attempts >= 1 || attempts <= 6) {
                                    valid = true;
                                }
                                break;
                            case 3:
                                if (dataElement.equals("W") || dataElement.equals("C")) {
                                    result = dataElement.charAt(0);
                                    valid = true;
                                }
                                break;
                            case 4:
                                if (r != 0) {
                                    if (last+1 == Integer.parseInt(dataElement)) {
                                        valid = true;
                                        last = Integer.parseInt(dataElement);
                                    }
                                }
                                else if (r == 0) {
                                    valid = true;
                                }
                                break;
                            default:
                                break;
                        }
                        if (valid) {
                            stats[r][item] = dataElement;
                        }
                        else {
                            break;
                        }
                        dataBuilder.setLength(0);
                        item++;
                    }
                    else {
                        dataBuilder.append(data.charAt(c));
                    }
                }
                if (!valid || item != 5 || c != data.length()) {
                    throw new Exception("Error");
                }
                else {
                    updateAttemptsMap(attempts);
                    updateCurrentStreak(result);
                    r++;
                }
            }
            scan.close();
        }
        catch (ArrayIndexOutOfBoundsException e){
            removeStatsError(type, r, file);
            stats[r-1][0] = "ERROR";
        }
        catch (Exception e) {
            removeStatsError(type, r, file);
            stats[r][0] = "ERROR";
        }
        return stats;
    }

    private void removeStatsError(int type, int index, File file) {
        ArrayList<String> oldData = new ArrayList<>();
        try {
            Scanner scan = new Scanner(file);
            int i = 0;
            while(scan.hasNext() && i < index) {
                oldData.add(scan.nextLine());
                i++;
            }
            FileWriter fw = new FileWriter(file);
            fw.write("");
            for (String d : oldData) {
                fw.append(d+"\n");
            }
            fw.flush();
            fw.close();
            
            writeStats(type, "ERR0R", index-1, 'L');
            scan.close();
        }
        catch (Exception e) {
            return;
        }
    }
    
    private void newAttemptsMap() {
        AttemptsMap.clear();
        for(int i = 1; i <= 6; i++) {
            AttemptsMap.put(i, 0);
        }
        return;
    }
    
    private void updateAttemptsMap(int attempts) {
        AttemptsMap.put(attempts, AttemptsMap.get(attempts)+1);
        return;
    }
    
    public int getAttemptsNum(int attempts) {
        return AttemptsMap.get(attempts);
    }
    
    public int getNumGames() {
        int numGames = 0;
        for(int i = 1; i <= 6; i++) {
            numGames += getAttemptsNum(i);
        }
        return numGames;
    }

    private void newStreaks() {
        currentStreak = 0;
        maxStreak = 0;
    }
    
    private void updateCurrentStreak(char result) {
        if  (result == 'W') {
            numWins++;
            currentStreak++;
            updateMaxStreak();
        }
        else if (result == 'L') {
            currentStreak = 0;
        }
    }

    private void updateMaxStreak() {
        if (currentStreak>maxStreak) {
            maxStreak++;
        }
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }
    
    public void newNumWins() {
        numWins = 0;
    }
    
    public int getNumWins() {
        return numWins;
    }
    


    
    
    
}