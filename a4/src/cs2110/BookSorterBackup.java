package cs2110;

import cs2110.BookSorter.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BookSorterBackup {
    /**
     * Parse the file of book data
     * @param filename - input filename relative to current working directory
     * @precondition - input file exists and has:
     *    - a header row "Title, Author" and 1 or more rows of data
     *    - data rows include a title followed by a comma followed by an author
     *    - title and author do not contain commas
     *    - no lines besides header and data
     * @return an array with one string array for each book
     *    - a book is represented as an array whose first element is title
     *      and whose second element is author
     */
    public static BookSorter.Book[] parseBookList(String filename) {
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
            BookSorter.Book[] ret = new BookSorter.Book[numLines-1];
            for (int i = 0; dataScanner.hasNextLine(); i++) {
                String line = dataScanner.nextLine();
                String[] parts = line.split(",");
                ret[i] = new BookSorter.Book(parts[0], parts[1]);
            }
            dataScanner.close();
            return ret;
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        return new BookSorter.Book[] {};
    }

    /**
     * Data class representing a book
     * @param title - the name of the book
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
     * TODO: Document me!
     */
    private static int selectFirst(BookSorter.Book[] books, int start, int firstSoFar, int ordering) {
        if (start >= books.length) {
            return firstSoFar;
        }
        String currTitle = books[start].title().toLowerCase();
        String bestTitle = books[firstSoFar].title().toLowerCase();
        int titleCmp = currTitle.compareTo(bestTitle);
        if (titleCmp == 0) {
            String currAuthor = books[start].author().toLowerCase();
            String bestAuthor = books[firstSoFar].author().toLowerCase();
            if (currAuthor.compareTo(bestAuthor) < 0) {
                firstSoFar = start;
            }
        } else {
            if (titleCmp < 0) {
                firstSoFar = start;
            }
        }
        return selectFirst(books, start + 1, firstSoFar, ordering);
    }

    /**
     * TODO: Document me!
     */
    public static void selectionSort(BookSorter.Book[] books, int ordering) {
        selectionSortHelper(books, 0, ordering);
    }

    private static void selectionSortHelper(BookSorter.Book[] books, int start, int ordering) {
        if (start >= books.length - 1) {
            return;
        }
        int firstIdx = selectFirst(books, start, start, ordering);
        BookSorter.Book tmp = books[start];
        books[start] = books[firstIdx];
        books[firstIdx] = tmp;
        selectionSortHelper(books, start + 1, ordering);
    }

    public static void main(String[] args) {
        // Edit this filename to use a different data set
        String filename = "data/books.csv";

        BookSorter.Book[] books = parseBookList(filename);
        System.out.println("=== Before Sorting ===");
        for(int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
        selectionSort(books, 1);

        System.out.println();
        System.out.println("=== After Sorting ===");
        for(int i = 0; i < books.length; i++) {
            System.out.println(books[i]);
        }
    }
}
