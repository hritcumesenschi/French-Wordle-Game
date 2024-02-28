package wordle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * The main game class for a word guessing game called wordle.
 *
 * @author Chementel Ylies
 */
public class Game {

    static int GAME_WON = 0;
    static int SCORE = 0;

    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.gameWords();
        parser.dictionaryWords();
        Game jeu = new Game();
        //jeu.game();
    }

    /**
     * Selects a random word of a specified number of letters from a file.
     *
     * @param nbLetter The number of letters in the word to select.
     * @return The selected word.
     */
    public String selectWord(int nbLetter) {
        String directory = "Game_Words_files/";
        String filePath = directory + "Words_with_" + nbLetter + "_letters.txt";
        String word = null;
        String words[] = new String[1000];
        Random random = new Random();
        int index = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words[index++] = line;
            }
            int randomInt = random.nextInt(index);
            word = words[randomInt];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return word;
    }

    /**
     * Checks if an input word exists in the dictionary of words with the same
     * length.
     *
     * @param inputWord    The input word to check.
     * @param selectedWord The selected word to compare against.
     * @return True if the input word exists in the dictionary; otherwise, false.
     */
    public Boolean wordCheck(String inputWord, String selectedWord) {
        if (inputWord.length() > selectedWord.length() || inputWord.length() < selectedWord.length()) {
            return false;
        }
        String filePath = "Dictionary_Words_files/" + "Words_with_" + selectedWord.length() + "_letters.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(inputWord)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Determines the status of each letter in the input word compared to the
     * selected word.
     *
     * @param inputWord    The input word to compare.
     * @param selectedWord The selected word for comparison.
     * @return An array of status values for each letter in the input word.
     */
    public String[] wordStatus(String inputWord, String selectedWord) {
        String status[] = new String[inputWord.length()];
        for (int i = 0; i < inputWord.length(); i++) {
            if (inputWord.charAt(i) == selectedWord.charAt(i)) {
                status[i] = "Vert";// Correct
                SCORE = SCORE + 10;
            } else if (selectedWord.contains(String.valueOf(inputWord.charAt(i)))) {
                status[i] = "Jaune";// Wrong Place
                SCORE = SCORE + 5;
            } else {
                status[i] = "Rouge";// Incorrect
            }
            System.out.print(status[i] + " ");
        }
        System.out.println();
        return status;
    }

    /**
     * Determines the difficulty level of the game based on the number of times the
     * player has won.
     *
     * @return The difficulty level, which is the number of letters in the selected
     * word.
     */
    public static int gameDifficulty() {
        if (GAME_WON == 0) {
            return 4;// return the number of letter of the selected word
        } else if (GAME_WON == 1) {
            return 5;
        } else if (GAME_WON == 2) {
            return 6;
        } else if (GAME_WON == 3) {
            return 7;
        } else if (GAME_WON == 4) {
            return 8;
        } else {
            return 9;
        }
    }

    public static void BestScore(int score) {
        String filePath = "BestScores.txt";
        String bestScoreList[] = new String[11];
        int i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bestScoreList[i]=line;
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Scanner scanner = new Scanner(System.in);

            String line;
            line= ""+ score;
            bestScoreList[i]=line;
            int j=0;
            while(j<10) {
                if(bestScoreList[j]!=null) {
                    String subString1 = bestScoreList[i].trim();
                    String subString2 = bestScoreList[j].trim();
                    if(Integer.parseInt(subString1)>Integer.parseInt(subString2)) {
                        String tmp=bestScoreList[j];
                        bestScoreList[j]=bestScoreList[i];
                        bestScoreList[i]=tmp;
                    }
                    j++;
                }
                else {
                    break;
                }
            }
            try (FileWriter writer = new FileWriter(filePath, false)) {
                i=0;
                while(i<10) {
                    if(bestScoreList[i]!=null) {
                        writer.write(bestScoreList[i]+"\n");
                        i++;
                    }
                    else {
                        break;
                    }
                }
                writer.close();
            }
            //scanner1.close();//Problem to be solved, can cause bugs
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
