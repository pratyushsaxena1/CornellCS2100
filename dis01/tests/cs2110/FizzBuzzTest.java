package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FizzBuzzTest {

    PrintStream systemOut;
    PrintStream out;
    ByteArrayOutputStream outBytes;

    @BeforeEach
    void setUp() {
        outBytes = new ByteArrayOutputStream();
        out = new PrintStream(outBytes);
        systemOut = System.out;
        System.setOut(out);
    }

    @AfterEach
    void restoreOutput() {
        out.close();
        System.setOut(systemOut);
    }

    @DisplayName("The `fizzBuzz()` method prints the correct outputs for inputs 1-1000.")
    @Test
    public void testFizzBuzz() {
        try {
            Scanner sc = new Scanner(new File("fizzbuzz.txt"));
            Practice.fizzBuzz(1000);
            String[] outLines = outBytes.toString().split(System.lineSeparator());
            for (int i = 0; i < 1000; i++) {
                assertEquals(sc.nextLine(), outLines[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not locate file \"fizzbuzz.txt\".");
            System.exit(1);
        }
    }

    @DisplayName("The `fizzBuzzHard()` method prints the correct outputs for inputs 1-1000.")
    @Test
    public void testFizzBuzzHard() {
        try {
            Scanner sc = new Scanner(new File("fizzbuzzhard.txt"));
            Practice.fizzBuzzHard(1000);
            String[] outLines = outBytes.toString().split(System.lineSeparator());
            for (int i = 0; i < 1000; i++) {
                assertEquals(sc.nextLine(), outLines[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not locate file \"fizzbuzzhard.txt\".");
            System.exit(1);
        }
    }
}
