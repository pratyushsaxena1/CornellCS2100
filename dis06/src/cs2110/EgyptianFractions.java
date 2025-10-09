package cs2110;

import java.util.Scanner;

public class EgyptianFractions {

    /**
     * Prints the "greedy" Egyptian fraction representation of the given rational number `r`, which
     * is a sum of fractions whose numerators are all 1 and whose denominators are all distinct and
     * increasing. The fractions should be printed using the `Rational.toString()` method, and
     * consecutive fractions should be separated by " + ". For example, given input 5/7, the method
     * should print "1/2 + 1/5 + 1/70". Requires that `r` is positive.
     */
    public static void printEgyptianFractionRepresentation(Rational r) {
        assert r.numerator() > 0; // defensive programming
        int d = 1;
        while (1 != r.numerator() || d != r.denominator()) {
            Rational newr = new Rational(1, d);
            System.out.println(newr.toString() + " + ");
            r = r.difference(newr);;
            d++;
        }
    }

    public static void main(String[] args) {
        Rational rational = null;

        try (Scanner sc = new Scanner(System.in)) {
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Enter a positive integer or fraction: ");
                String input = sc.nextLine();
                input = input.replaceAll("\\s+", ""); // remove whitespace

                int barLoc = input.indexOf("/");
                try {
                    if (barLoc == -1) {
                        rational = new Rational(Integer.parseInt(input), 1);
                    } else {
                        rational = new Rational(Integer.parseInt(input.substring(0, barLoc)),
                                Integer.parseInt(input.substring(barLoc + 1)));
                    }
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Your input could not be parsed. Try again.\n");
                }
            }
        }

        System.out.print("The greedy Egyptian fraction representation is: ");
        printEgyptianFractionRepresentation(rational);
    }
}
