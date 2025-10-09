package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalindromeCheckerTest {

    @DisplayName("WHEN given a palindrome with an odd number of characters, THEN `isPalindrome()` "
            + "returns true.")
    @Test
    void testClassicOddLengthPalindrome() {
        assertTrue(PalindromeChecker.isPalindrome("racecar"));
    }

    @DisplayName("WHEN given a palindrome with an even number of characters, THEN `isPalindrome()` "
            + "returns true.")
    @Test
    void testClassicEvenLengthPalindrome() {
        assertTrue(PalindromeChecker.isPalindrome("noon"));
    }

    @DisplayName("WHEN given a palindrome with mixed cases, THEN `isPalindrome()` should ignore "
            + "case differences and still return true.")
    @Test
    void testCaseInsensitivePalindrome() {
        assertTrue(PalindromeChecker.isPalindrome("Madam"));
    }

    @DisplayName("WHEN given a non-palindrome, THEN `isPalindrome()` returns false.")
    @Test
    void testNonPalindrome() {
        assertFalse(PalindromeChecker.isPalindrome("java"));
    }

    @DisplayName("WHEN given a String with one character, THEN `isPalindrome()` returns true.")
    @Test
    void testSingleCharacterIsPalindrome() {
        assertTrue(PalindromeChecker.isPalindrome("a"));
    }

    @DisplayName("WHEN given the empty String, THEN `isPalindrome()` returns true.")
    @Test
    void testEmptyStringIsPalindrome() {
        assertTrue(PalindromeChecker.isPalindrome(""));
    }

    @DisplayName("WHEN given a String that is close to (but not) a palindrome , THEN "
            + "`isPalindrome()` returns false.")
    @Test
    void testNonPalindromeThatFailsInTheMiddle() {
        assertFalse(PalindromeChecker.isPalindrome("abcdecba"));
    }

    @DisplayName("WHEN given a palindromic String consisting of numbers, THEN "
            + "`isPalindrome()` returns true.")
    @Test
    void testPalindromeWithNumbers() {
        assertTrue(PalindromeChecker.isPalindrome("12321"));
    }

    // BONUS QUESTION. Uncomment the unit test below for exercise 1(e)
//    @DisplayName("WHEN given a palindromic String containing spaces and punctuation, THEN "
//            + "`isPalindrome()` ignores these non alpha-numeric characters and returns true.")
//    @Test
//    void testBonusQuestion() {
//        // From https://leetcode.com/problems/valid-palindrome/description/
//        assertTrue(PalindromeChecker.isPalindrome("A man, a plan, a canal: Panama"));
//    }
}