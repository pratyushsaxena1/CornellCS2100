package cs2110;

public class PalindromeChecker {

    /**
     * Returns whether `s` is a palindrome (ignoring case-sensitivity), meaning it reads the same
     * forwards and backwards. (Bonus challenge: ignore all non-alphanumeric characters)
     */
    static boolean isPalindrome(String s) {
        s = s.toLowerCase().strip();
        s = s.replaceAll(",", "");
        s = s.replaceAll("\\.", "");
        s = s.replaceAll(":", "");
        if (s.length() <= 1) {
            return true;
        }
        else {
            if (s.charAt(0) == s.charAt(s.length() - 1)) {
                String newS = s.substring(1, s.length() - 1);
                return isPalindrome(newS);
            }
            else {
                return false;
            }
        }
    }

    /**
     * Returns whether `s.substring(start, end)` is a palindrome. This method should be recursive and
     * should not construct any new `String` objects during its execution. Requires that `s`
     * consists of only characters from 'a'-'z', and `0 <= start <= end < s.length`.
     */
    static boolean rangeIsPalindrome(String s, int start, int end) {
        if (s.length() <= 1) {
            return true;
        }
        else if (s.charAt(0) == s.charAt(s.length() - 1)) {
            String newS = s.substring(1, s.length() - 1);
            return rangeIsPalindrome(newS, start + 1, end);
        }
        return false;
    }

}