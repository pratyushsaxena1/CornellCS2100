package cs2110;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BookSorter {

    /**
     * Parse the file of book data
     *
     * @param filename - input filename relative to current working directory
     * @return an array with one string array for each book - a book is represented as an array
     * whose first element is title and whose second element is author
     * @precondition - input file exists and has: - a header row "Title, Author" and 1 or more rows
     * of data - data rows include a title followed by a comma followed by an author - title and
     * author do not contain commas - no lines besides header and data
     */
    public static Book[] parseBookList(String filename) {
        File inputFile = new File(filename).getAbsoluteFile();
        System.out.println(inputFile.getAbsolutePath());
        try {
            Scanner lineCounter = new Scanner(inputFile);
            int numLines;
            for (numLines = 0; lineCounter.hasNextLine(); numLines++) {
                lineCounter.nextLine();
            }
            lineCounter.close();

            Scanner dataScanner = new Scanner(inputFile);
            dataScanner.nextLine(); // ignore header row
            Book[] ret = new Book[numLines - 1];
            for (int i = 0; dataScanner.hasNextLine(); i++) {
                String line = dataScanner.nextLine();
                String[] parts = line.split(",");
                ret[i] = new Book(parts[0], parts[1]);
            }
            dataScanner.close();
            return ret;
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        return new Book[]{};
    }

    /**
     * Data class representing a book
     *
     * @param title  - the name of the book
     * @param author - string representing the full name(s) of the author(s) of the book
     */
    record Book(String title, String author) {

        /**
         * @return a String representing this Book
         */
        public String toString() {
            // this looks nicer than the default implementation of Record.toString
            return title() + " by " + author();
        }
    }

    /**
     * Finds index of first book in ascending order from a given index "start" to the end of an
     * array "books". Compares one book at a time and keeps track of the smallest book so far, then
     * works recursively until the end of the array is reached. Returns index of the smallest book
     * according to "ordering". Requires that "books" is not null, any elements in "books" are not
     * null, any titles and authors are not null, "start" is within the bounds of "books", and
     * "ordering" is equal to either 1 or 2.
     */
    private static int selectFirst(Book[] books, int start, int firstSoFar, int ordering) {
        if (start >= books.length) {
            return firstSoFar;
        }
        if (compareBooks(books[start], books[firstSoFar], ordering) < 0) {
            firstSoFar = start;
        }
        return selectFirst(books, start + 1, firstSoFar, ordering);
    }

    /**
     * Compares two Book objects to find which one comes first based on the "ordering". If the value
     * is 1, the books are sorted by title; if it's 2, the books are sorted by author. Returns a
     * negative value if the first book is before the second, a positive value if it is after, and
     * zero if they're equal. Requires that "b1" and "b2" have a non-null title and non-null author
     * and that "ordering" is equal to 1 or 2.
     */
    private static int compareBooks(Book b1, Book b2, int ordering) {
        if (ordering == 1) {
            return b1.title().toLowerCase().compareTo(b2.title().toLowerCase());
        } else {
            return b1.author().toLowerCase().compareTo(b2.author().toLowerCase());
        }
    }

    /**
     * Sorts "books", an array of Book objects, into some ascending order by applying a recursive
     * selection sort. The way the books are ordered depends on the value of "ordering." If the
     * value is 1, the books are sorted by title; if it's 2, the books are sorted by author. Assume
     * that the array of books is not null and that the ordering value is either 1 or 2. By the end,
     * the array of books will be sorted in ascending order according to the chosen ordering.
     * Requires that "b1" and "b2" have a non-null title and non-null author and that "ordering" is
     * equal to 1 or 2.
     */
    public static void selectionSort(Book[] books, int ordering) {
        if (books.length != 0) {
            selectionSortHelper(books, 0, ordering);
        }
    }

    /**
     * Sorts the subarray of Book objects from index "start" to the end of the array. Finds the
     * first book in ascending order from "start" to the end of the array, swaps it into position,
     * and then recursively sorts the rest of the subarray until a single element is left. Requires
     * that "books" is not null, any elements in "books" are not null, any titles and authors are
     * not null, "start" is within the bounds of "books", and "ordering" is equal to either 1 or 2.
     */
    private static void selectionSortHelper(Book[] books, int start, int ordering) {
        if (start >= books.length - 1) {
            return;
        }
        int indexOfFirst = selectFirst(books, start, start, ordering);
        Book tempBook = books[start];
        books[start] = books[indexOfFirst];
        books[indexOfFirst] = tempBook;
        selectionSortHelper(books, start + 1, ordering);
    }

    public static void main(String[] args) {
        // Edit this filename to use a different data set
        String filename = "data/books.csv";

        Book[] books = parseBookList(filename);
        System.out.println("=== Before Sorting ===");
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
        selectionSort(books, 1);

        System.out.println();
        System.out.println("=== After Sorting ===");
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
    }
}