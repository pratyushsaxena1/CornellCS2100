package cs2110;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EgyptianFractionTest {

    /* *******************************************************************************************
     * The code at the top of this file is used to capture the console output, so we can check
     * that it is correct.
     ******************************************************************************************* */

    /**
     * The original `System.out`.
     */
    PrintStream systemOut;

    /**
     * Replacement for `System.out` during test execution.
     */
    PrintStream out;
    ByteArrayOutputStream outBytes;

    @BeforeEach
    void setUpSimulator() {
        outBytes = new ByteArrayOutputStream();
        out = new PrintStream(outBytes);
        systemOut = System.out;
        System.setOut(out);
        clearOutputStream();
    }

    /**
     * Resets the output stream so we can capture the print output from processing one command
     */
    void clearOutputStream() {
        out.flush();
        outBytes.reset();
    }

    @AfterEach
    void restoreOutput() {
        out.close();
        System.setOut(systemOut);
    }

    /**
     * Asserts that the line captured in the OutputStream is equal to `expected`
     */
    void assertOutput(String expected) {
        out.flush();
        assertEquals(expected, outBytes.toString());
        outBytes.reset();
    }

    /* *******************************************************************************************
     * Here is where the tests begin.
     ******************************************************************************************* */

    @DisplayName("WHEN we compute the greedy Egyptian fraction representation of a unit fraction, "
            + "THEN this fraction itself is printed.")
    @Test
    public void testUnitFraction() {
        EgyptianFractions.printEgyptianFractionRepresentation(new Rational(1,3));
        assertOutput("1/3");

        EgyptianFractions.printEgyptianFractionRepresentation(new Rational(1,7));
        assertOutput("1/7");
    }

    @DisplayName("WHEN we compute a greedy Egyptian fraction representation with two terms, "
            + "THEN it is displayed correctly.")
    @Test
    public void testTwoTerms() {
        EgyptianFractions.printEgyptianFractionRepresentation(new Rational(3,4));
        assertOutput("1/2 + 1/4");

        EgyptianFractions.printEgyptianFractionRepresentation(new Rational(2,3));
        assertOutput("1/2 + 1/6");

        EgyptianFractions.printEgyptianFractionRepresentation(new Rational(5,4));
        assertOutput("1/1 + 1/4");
    }

    @DisplayName("WHEN we compute a greedy Egyptian fraction representation with many terms, "
            + "THEN it is displayed correctly.")
    @Test
    public void testManyTerms() {
        EgyptianFractions.printEgyptianFractionRepresentation(new Rational(5,7));
        assertOutput("1/2 + 1/5 + 1/70");

        EgyptianFractions.printEgyptianFractionRepresentation(new Rational(37,53));
        assertOutput("1/2 + 1/6 + 1/32 + 1/5088");
    }
}
