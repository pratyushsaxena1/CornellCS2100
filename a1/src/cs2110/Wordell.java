package cs2110;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * A console-based implementation of the popular "Wordle" word game.
 */
public class Wordell {

    /**
     * Returns a random entry from the valid word list in "words.txt".
     */
    static String getRandomValidWord() throws IOException {
        String[] validWords = Files.readString(Path.of("words.txt")).split(System.lineSeparator());
        Random rand = new Random();
        return validWords[rand.nextInt(validWords.length)];
    }

    /**
     * Returns a String that outputs a green tile containing the given character `c` to the console,
     * followed by a trailing space. Requires that `c` is an uppercase letter.
     */
    static String greenTile(char c) {
        assert 'A' <= c && c <= 'Z'; // make sure the parameter is an uppercase letter
        return "\u001B[102m " + c + " \u001B[0m ";
    }

    /**
     * Returns a String that outputs a gray tile containing the given character `c` to the console,
     * followed by a trailing space. Requires that `c` is an uppercase letter.
     */
    static String yellowTile(char c) {
        assert 'A' <= c && c <= 'Z'; // make sure the parameter is an uppercase letter
        return "\u001B[103m " + c + " \u001B[0m ";
    }

    /**
     * Returns a String that outputs a yellow tile containing the given character `c` to the
     * console, followed by a trailing space. Requires that `c` is an uppercase letter.
     */
    static String grayTile(char c) {
        assert 'A' <= c && c <= 'Z'; // make sure the parameter is an uppercase letter
        return "\u001B[47m " + c + " \u001B[0m ";
    }

    /**
     * Returns `false` and prints an explanatory message if the given `guess` is not valid,
     * otherwise returns `true`. A guess is not valid if (1) it contains a number of characters
     * besides 5, in which case the message "Your guess must have 5 letters. Try again.", or (2) it
     * contains the correct number of characters, but one of these is outside 'A'-'Z', in which case
     * the message "Your guess cannot include the character '*'. Try again." with * replaced by the
     * first illegal character should be printed. Both messages should end with a newline. Requires
     * that `guess != null`.
     */
    static boolean isValidGuess(String guess) {
        assert guess != null;
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (guess.length() != 5) {
            System.out.println("Your guess must have 5 letters. Try again.");
            return false;
        }
        for (int i = 0; i < guess.length(); i++) {
            if (!letters.contains(String.valueOf(guess.charAt(i)))) {
                System.out.println("Your guess cannot include the character '" + guess.charAt(i)
                        + "'. Try again.");
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the output for the given `guess`, which consists of `guess.length()` colored tiles
     * containing the characters in the guess (in order) colored according to the corresponding
     * entries in the given `colors` array (0=gray, 1=yellow, 2=green), followed by a trailing space
     * and a newline.
     */
    static void printGuessOutput(String guess, int[] colors) {
        for (int i = 0; i < guess.length(); i++) {
            if (colors[i] == 0) {
                System.out.print(grayTile(guess.charAt(i)));
            } else if (colors[i] == 1) {
                System.out.print(yellowTile(guess.charAt(i)));
            } else if (colors[i] == 2) {
                System.out.print(greenTile(guess.charAt(i)));
            }
        }
        System.out.println();
    }

    /**
     * Returns an `int[5]` array where the value at index `i` corresponds to the color of the `i`th
     * character in the given `guess` (0=gray, 1=yellow, 2=green). A character in the `guess` is
     * colored green if that character appears in the same position in the actual `word`. A
     * character is colored yellow if it appears in a different position in the actual `word` that
     * is not already associated with another yellow or green tile. Otherwise, a character is
     * colored gray. All yellow tiles with a given letter appear to the left of all gray tiles with
     * that same letter.
     */
    public static int[] getColorArray(String guess, String word) {
        int[] colors = new int[guess.length()];
        String goodLetters = "";
        for (int i = 0; i < guess.length(); i++) {
            // Check if green
            if (guess.charAt(i) == word.charAt(i)) {
                colors[i] = 2;
                goodLetters = goodLetters.concat(String.valueOf(guess.charAt(i)));
            } else {
                colors[i] = 0;
            }
        }
        for (int i = 0; i < guess.length(); i++) {
            // Check if yellow
            if (word.contains(String.valueOf(guess.charAt(i)))) {
                if (!goodLetters.contains(String.valueOf(guess.charAt(i)))) {
                    colors[i] = 1;
                    goodLetters = goodLetters.concat(String.valueOf(guess.charAt(i)));
                }
            } else {
                colors[i] = 0;
            }
        }
        return colors;
    }

    /**
     * Simulates a game of "Wordell" with the given target `word`, using the given Scanner `sc` to
     * get inputs (guessed words) from the user. Over the course of 6 rounds, the game should prompt
     * the user for a guess with the console output "#. Enter a guess: " where # is the current
     * valid guess number (starting from 1), read the user's console input, and convert it to
     * uppercase. If the user gives a valid guess, a color array is printed and the prompt is made
     * for the next guess. If the user gives an invalid guess, the application should prompt the
     * user for another guess with the same guess number. If the user guesses the correct word, the
     * game should print "Congratulations! You won in # guesses." with `#` replaced by the number of
     * valid guesses, and the method should return. Otherwise, if the user runs out of guesses, the
     * game should print "Better luck next time. The word was *****.", with `*****` replaced by the
     * `word`.
     */
    static void play(String word, Scanner sc) {
        int numGuesses = 0;
        boolean gameOver = false;
        while (!gameOver) {
            if (numGuesses == 6) {
                System.out.println("Better luck next time. The word was " + word + ".");
                return;
            }
            System.out.print((numGuesses + 1) + ". Enter a guess: ");
            String guess = String.valueOf(sc.next()).toUpperCase();
            if (isValidGuess(guess)) {
                numGuesses += 1;
                int[] colors = getColorArray(guess, word);
                printGuessOutput(guess, colors);
                if (colors[0] == 2 && colors[1] == 2 && colors[2] == 2 && colors[3] == 2
                        && colors[4] == 2) {
                    System.out.println("Congratulations! You won in " + numGuesses + " guesses.");
                    gameOver = true;
                }
            }
        }
    }


    /**
     * Simulates a game of "Wordell" in hard mode with the given target `word`, using the given
     * Scanner `sc` to get inputs (guessed words) from the user. Over the course of 6 rounds, the
     * game should prompt * the user for a guess with the console output "#. Enter a guess: " where
     * # is the current * valid guess number (starting from 1), read the user's console input, and
     * convert it to * uppercase. If the user gives a valid guess, a color array is printed and the
     * prompt is made for the next guess. If the user gives an invalid guess, the application should
     * prompt the user for another guess with the same guess number. In the case that a guess
     * conflicts with information from a previous guess, the message "Your guess conflicts with
     * information from the guess *****. Try again." with ***** replaced by the first guessed word
     * that causes a conflict is printed. If the user guesses the correct word, the game should
     * print "Congratulations! You won in # guesses." with the correct number of guesses filled in,
     * and the method should return. Otherwise, if the user runs out of guesses, the game should
     * print "Better luck next time. The word was *****.", with `word` in place of the *s.
     */
    // Check if a guess is valid with the extra restrictions that come with hard mode
    static boolean isValidHardGuess(String guess, String[] allGuesses, String word) {
        if (isValidGuess(guess)) {
            for (int i = 0; i < allGuesses.length; i++) {
                if (allGuesses[i] != null) {
                    if (!Arrays.equals(getColorArray(allGuesses[i], guess),
                            getColorArray(allGuesses[i], word))) {
                        System.out.println("Your guess conflicts with information from the guess "
                                + allGuesses[i] + ". Try again.");
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    static void playHardMode(String word, Scanner sc) {
        int numGuesses = 0;
        String[] allGuesses = new String[6];
        boolean gameOver = false;
        while (!gameOver) {
            if (numGuesses == 6) {
                System.out.println("Better luck next time. The word was " + word + ".");
                return;
            }
            System.out.print((numGuesses + 1) + ". Enter a guess: ");
            String guess = String.valueOf(sc.next()).toUpperCase();
            if (isValidHardGuess(guess, allGuesses, word)) {
                allGuesses[numGuesses] = guess;
                numGuesses += 1;
                int[] colors = getColorArray(guess, word);
                printGuessOutput(guess, colors);
                if (colors[0] == 2 && colors[1] == 2 && colors[2] == 2 && colors[3] == 2
                        && colors[4] == 2) {
                    System.out.println("Congratulations! You won in " + numGuesses + " guesses.");
                    gameOver = true;
                }
            }
        }
    }

    /**
     * Creates a new "Wordell" game with a random target word. Uses hard mode if the "hard" command
     * line argument is supplied.
     */
    public static void main(String[] args) throws IOException {
        boolean hardMode = (args.length == 1 && args[0].equals("hard"));

        try (Scanner sc = new Scanner(System.in)) {
            if (hardMode) {
                playHardMode(getRandomValidWord(), sc);
            } else {
                play(getRandomValidWord(), sc);
            }
        }
    }
}
