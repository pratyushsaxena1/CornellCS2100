package cs2110;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs2110.BookSorter.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test suite for recursive selection sort.
 * Includes both basic and edge cases.
 */
public class SelectionSortSmokeTest {

    // ========== Original Tests ==========

    @DisplayName("WHEN list is empty, THEN sorting does nothing")
    @Test
    void testEmptyList() {
        Book[] input = new Book[] {};
        Book[] expected = new Book[] {};

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN list has one book, THEN sorting does nothing")
    @Test
    void testSingleElement() {
        Book[] input = new Book[] {
                new Book("Title A", "Author A")
        };
        Book[] expected = new Book[] {
                new Book("Title A", "Author A")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN list is already sorted by title, THEN sorting preserves order")
    @Test
    void testAlreadySortedTitle() {
        Book[] input = new Book[] {
                new Book("Apple", "Author A"),
                new Book("Banana", "Author B"),
                new Book("Cherry", "Author C")
        };
        Book[] expected = new Book[] {
                new Book("Apple", "Author A"),
                new Book("Banana", "Author B"),
                new Book("Cherry", "Author C")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN list is unsorted by title, THEN sorting reorders it correctly")
    @Test
    void testUnsortedTitle() {
        Book[] input = new Book[] {
                new Book("Banana", "Author B"),
                new Book("Cherry", "Author C"),
                new Book("Apple", "Author A")
        };
        Book[] expected = new Book[] {
                new Book("Apple", "Author A"),
                new Book("Banana", "Author B"),
                new Book("Cherry", "Author C")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN list is unsorted by author, THEN sorting reorders it correctly")
    @Test
    void testUnsortedAuthor() {
        Book[] input = new Book[] {
                new Book("Book B", "Zebra"),
                new Book("Book C", "Monkey"),
                new Book("Book A", "Apple")
        };
        Book[] expected = new Book[] {
                new Book("Book A", "Apple"),
                new Book("Book C", "Monkey"),
                new Book("Book B", "Zebra")
        };

        BookSorter.selectionSort(input, 2);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN list has duplicates, THEN sorting preserves correct order")
    @Test
    void testDuplicates() {
        Book[] input = new Book[] {
                new Book("Apple", "Author Z"),
                new Book("Banana", "Author M"),
                new Book("Apple", "Author A")
        };
        Book[] expected = new Book[] {
                new Book("Apple", "Author Z"),
                new Book("Apple", "Author A"),
                new Book("Banana", "Author M")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN list has mixed case, THEN sorting is case-insensitive")
    @Test
    void testCaseInsensitivity() {
        Book[] input = new Book[] {
                new Book("banana", "Author B"),
                new Book("Cherry", "Author C"),
                new Book("apple", "Author A")
        };
        Book[] expected = new Book[] {
                new Book("apple", "Author A"),
                new Book("banana", "Author B"),
                new Book("Cherry", "Author C")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    // ========== Extra Edge Tests ==========

    @DisplayName("WHEN all books are identical, THEN sorting preserves the array")
    @Test
    void testAllIdentical() {
        Book[] input = new Book[] {
                new Book("Same Title", "Same Author"),
                new Book("Same Title", "Same Author"),
                new Book("Same Title", "Same Author")
        };
        Book[] expected = new Book[] {
                new Book("Same Title", "Same Author"),
                new Book("Same Title", "Same Author"),
                new Book("Same Title", "Same Author")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN array is reverse sorted by title, THEN sorting reorders into ascending order")
    @Test
    void testReverseSortedTitle() {
        Book[] input = new Book[] {
                new Book("Zebra", "Author Z"),
                new Book("Monkey", "Author M"),
                new Book("Apple", "Author A")
        };
        Book[] expected = new Book[] {
                new Book("Apple", "Author A"),
                new Book("Monkey", "Author M"),
                new Book("Zebra", "Author Z")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN array is reverse sorted by author, THEN sorting reorders into ascending order")
    @Test
    void testReverseSortedAuthor() {
        Book[] input = new Book[] {
                new Book("Book A", "Z Author"),
                new Book("Book B", "M Author"),
                new Book("Book C", "A Author")
        };
        Book[] expected = new Book[] {
                new Book("Book C", "A Author"),
                new Book("Book B", "M Author"),
                new Book("Book A", "Z Author")
        };

        BookSorter.selectionSort(input, 2);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN array contains special characters and numbers in titles, THEN sorting handles them correctly")
    @Test
    void testSpecialCharactersTitle() {
        Book[] input = new Book[] {
                new Book("!Exclamation", "Author A"),
                new Book("123Numbers", "Author B"),
                new Book("Apple", "Author C"),
                new Book("#HashTag", "Author D")
        };

        BookSorter.selectionSort(input, 1);

        for (int i = 0; i < input.length - 1; i++) {
            assertTrue(input[i].title().compareToIgnoreCase(input[i + 1].title()) <= 0);
        }
    }

    @DisplayName("WHEN array contains unicode titles and authors, THEN sorting orders by Unicode values")
    @Test
    void testUnicodeSorting() {
        Book[] input = new Book[] {
                new Book("Árbol", "José"),
                new Book("Zoo", "Álvaro"),
                new Book("Apple", "Zoë"),
                new Book("Éclair", "Émile")
        };

        BookSorter.selectionSort(input, 1);

        for (int i = 0; i < input.length - 1; i++) {
            assertTrue(input[i].title().compareToIgnoreCase(input[i + 1].title()) <= 0);
        }
    }

    @DisplayName("WHEN array length is large, THEN recursion sorts fully without error")
    @Test
    void testLargeArray() {
        Book[] input = new Book[] {
                new Book("M", "M"),
                new Book("Z", "Z"),
                new Book("A", "A"),
                new Book("C", "C"),
                new Book("X", "X"),
                new Book("B", "B"),
                new Book("Y", "Y"),
                new Book("F", "F"),
                new Book("D", "D"),
                new Book("E", "E")
        };

        BookSorter.selectionSort(input, 1);

        for (int i = 0; i < input.length - 1; i++) {
            assertTrue(input[i].title().compareToIgnoreCase(input[i + 1].title()) <= 0);
        }
    }

    @DisplayName("WHEN book has empty title, THEN sorting handles it correctly")
    @Test
    void testEmptyTitle() {
        Book[] input = new Book[] {
                new Book("", "Author A"),
                new Book("Banana", "Author B"),
                new Book("Apple", "Author C")
        };
        Book[] expected = new Book[] {
                new Book("", "Author A"),
                new Book("Apple", "Author C"),
                new Book("Banana", "Author B")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN book has empty author, THEN sorting handles it correctly")
    @Test
    void testEmptyAuthor() {
        Book[] input = new Book[] {
                new Book("Book A", ""),
                new Book("Book B", "Author B"),
                new Book("Book C", "Author A")
        };
        Book[] expected = new Book[] {
                new Book("Book A", ""),
                new Book("Book C", "Author A"),
                new Book("Book B", "Author B")
        };

        BookSorter.selectionSort(input, 2);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN both title and author are empty, THEN sorting still works")
    @Test
    void testEmptyTitleAndAuthor() {
        Book[] input = new Book[] {
                new Book("", ""),
                new Book("Banana", "Author B"),
                new Book("Apple", "Author A")
        };
        Book[] expected = new Book[] {
                new Book("", ""),
                new Book("Apple", "Author A"),
                new Book("Banana", "Author B")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

    @DisplayName("WHEN array only has empty-title books, THEN sorting preserves the array")
    @Test
    void testAllEmptyTitles() {
        Book[] input = new Book[] {
                new Book("", "Author A"),
                new Book("", "Author B"),
                new Book("", "Author C")
        };
        Book[] expected = new Book[] {
                new Book("", "Author A"),
                new Book("", "Author B"),
                new Book("", "Author C")
        };

        BookSorter.selectionSort(input, 1);

        assertArrayEquals(expected, input);
    }

}