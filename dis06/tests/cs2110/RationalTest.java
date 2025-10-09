package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RationalTest {

    @DisplayName("Constructor")
    @Nested
    class RationalConstructorTest {

        @DisplayName(
                "WHEN a positive Rational is constructed with arguments in simplified form, THEN "
                        + "its numerator and denominator are initialized correctly.")
        @Test
        public void testConstructionSimplifiedPositive() {
            Rational r = new Rational(2, 3);
            assertEquals(2, r.numerator());
            assertEquals(3, r.denominator());
        }

        @DisplayName(
                "WHEN a negative Rational is constructed with arguments in simplified form, THEN "
                        + "its numerator and denominator are initialized correctly.")
        @Test
        public void testConstructionSimplifiedNegative() {
            Rational r = new Rational(-2, 3);
            assertEquals(-2, r.numerator());
            assertEquals(3, r.denominator());
        }

        @DisplayName(
                "WHEN a Rational is constructed with a positive numerator argument and negative "
                        + "denominator argument, THEN its numerator is made negative and its denominator "
                        + "is made positive.")
        @Test
        public void testConstructionNegativeFlip() {
            Rational r = new Rational(2, -3);
            assertEquals(-2, r.numerator());
            assertEquals(3, r.denominator());
        }

        @DisplayName(
                "WHEN a Rational is constructed with a negative numerator and denominator arguments,"
                        + "THEN both its numerator and denominator are made positive.")
        @Test
        public void testConstructionNegativeCancel() {
            Rational r = new Rational(-2, -3);
            assertEquals(2, r.numerator());
            assertEquals(3, r.denominator());
        }

        @DisplayName("WHEN a Rational is constructed with the same (non-zero) numerator and "
                + "denominator arguments, THEN it should have numerator 1 and denominator 1.")
        @Test
        public void testConstructOne() {
            // both positive
            Rational r = new Rational(12, 12);
            assertEquals(1, r.numerator());
            assertEquals(1, r.denominator());

            // both negative
            r = new Rational(-6, -6);
            assertEquals(1, r.numerator());
            assertEquals(1, r.denominator());
        }

        @DisplayName("WHEN a Rational is constructed with the zero numerator argument and nonzero "
                + "denominator argument, THEN it should have numerator 0 and denominator 1.")
        @Test
        public void testConstructZero() {
            // positive denominator
            Rational r = new Rational(0, 4);
            assertEquals(0, r.numerator());
            assertEquals(1, r.denominator());

            //negative denominator
            r = new Rational(0, -3);
            assertEquals(0, r.numerator());
            assertEquals(1, r.denominator());
        }

        @DisplayName("WHEN a Rational is constructed with positive arguments that have common "
                + "factors, THEN it is reduced to simplified form.")
        @Test
        public void testConstructPositiveUnsimplified() {
            // reach unit fraction
            Rational r = new Rational(13, 52);
            assertEquals(1, r.numerator());
            assertEquals(4, r.denominator());

            // reach non-unit fraction
            r = new Rational(12, 68);
            assertEquals(3, r.numerator());
            assertEquals(17, r.denominator());
        }

        @DisplayName("WHEN a Rational is constructed with arguments (at least one of which is "
                + "negative) that have common factors, THEN it is correctly reduced to simplified "
                + "form.")
        @Test
        public void testConstructNegativeUnsimplified() {
            // negative numerator, positive denominator
            Rational r = new Rational(-45, 210);
            assertEquals(-3, r.numerator());
            assertEquals(14, r.denominator());

            // positive numerator, negative denominator
            r = new Rational(45, -210);
            assertEquals(-3, r.numerator());
            assertEquals(14, r.denominator());

            // negative numerator, negative denominator
            r = new Rational(-45, -210);
            assertEquals(3, r.numerator());
            assertEquals(14, r.denominator());
        }

        @DisplayName("WHEN a Rational is constructed whose numerator is greater than its "
                + "denominator, THEN it is correctly simplified.")
        @Test
        public void testConstructImproper() {
            Rational r = new Rational(12, 8);
            assertEquals(3, r.numerator());
            assertEquals(2, r.denominator());
        }

        @DisplayName("WHEN a Rational is constructed whose numerator is a multiple of its "
                + "denominator, THEN it is correctly simplified to a fraction with denominator 1.")
        @Test
        public void testConstructInteger() {
            // positive
            Rational r = new Rational(15, 3);
            assertEquals(5, r.numerator());
            assertEquals(1, r.denominator());

            // negative
            r = new Rational(-15, 3);
            assertEquals(-5, r.numerator());
            assertEquals(1, r.denominator());
        }
    }

    @DisplayName("toString()")
    @Nested
    class ToStringTest {

        @DisplayName("A positive rational number has the correct String representation.")
        @Test
        public void testPositiveToString() {
            Rational r = new Rational(2, 3);
            assertEquals("2/3", r.toString());
        }

        @DisplayName("A negative rational number has the correct String representation.")
        @Test
        public void testNegativeToString() {
            Rational r = new Rational(-2, 3);
            assertEquals("-2/3", r.toString());
        }

        @DisplayName("The rational number 0 has the correct String representation.")
        @Test
        public void testZeroToString() {
            Rational r = new Rational(0, 4);
            assertEquals("0/1", r.toString());
        }

        @DisplayName("WHEN a rational number is constructed with non-simplified arguments, THEN "
                + "its String representation shows its simplified form.")
        @Test
        public void testToStringSimplifies() {
            // positive
            Rational r = new Rational(4, 6);
            assertEquals("2/3", r.toString());

            // negative
            r = new Rational(4, -6);
            assertEquals("-2/3", r.toString());
        }
    }

    @DisplayName("equals()")
    @Nested
    class EqualsTest {

        @DisplayName("A `Rational` object should be equal to itself.")
        @Test
        public void testEqualsSelf() {
            Rational r = new Rational(2,3);
            assertTrue(r.equals(r));
            /* In the previous line, you likely see an IntelliJ warning 'assertTrue()' can be
             * simplified to 'assertEquals()'. It is true that these assertions will have the same
             * pass/fail behavior; however, they will result in different output messages in the
             * case of failure. We want to use `assertTrue()` so that our failure message will
             * alert us that our call to `equals()` returned `false` when it should have returned
             * `true`.
             */
        }

        @DisplayName("WHEN we construct two different `Rational` objects with the same arguments, "
                + "THEN these should be equal.")
        @Test
        public void testEqualsSameArgs() {
            Rational r1 = new Rational(2,3);
            Rational r2 = new Rational(2,3);
            assertTrue(r1.equals(r2));
            /* Try commenting out the `Rational.equals()` method. You should see that this test
             * fails. The default `Object.equals()` behavior is reference equality, which we do not
             * have here.
             */
        }

        @DisplayName("WHEN we construct two different `Rational` objects with different arguments, "
                + "but that simplify to the same fraction, THEN these should be equal.")
        @Test
        public void testEqualsEquivalentArgs() {
            Rational r1 = new Rational(2,3);
            Rational r2 = new Rational(-4,-6);
            assertTrue(r1.equals(r2));
        }

        @DisplayName("WHEN we construct two different `Rational` objects representing different "
                + "simplified fractions, THEN these should not be equal.")
        @Test
        public void testNotEqualsDifferentFracs() {
            // different numerators
            Rational r1 = new Rational(1,3);
            Rational r2 = new Rational(2,3);
            assertFalse(r1.equals(r2));

            // different denominators
            r1 = new Rational(2,3);
            r2 = new Rational(2,5);
            assertFalse(r1.equals(r2));

            // off by negative
            r1 = new Rational(2,3);
            r2 = new Rational(-2,3);
            assertFalse(r1.equals(r2));
        }

        @DisplayName("A `Rational` object should not be equal to `null`.")
        @Test
        public void testNotEqualsNull() {
            Rational r = new Rational(1,2);
            assertFalse(r.equals(null));
        }

        @DisplayName("A `Rational` object should not be equal to an object of a different type.")
        @Test
        public void testNotEqualsDifferentType() {
            Rational r = new Rational(1,2);
            assertFalse(r.equals("one half"));
        }
    }

    @DisplayName("sum()")
    @Nested
    class SumTest {

        @DisplayName("WHEN we compute the sum() of two positive rational numbers with the same "
                + "denominator, THEN the correct rational number is returned.")
        @Test
        public void testPositiveCommonDenom() {
            Rational r1 = new Rational(1,5);
            Rational r2 = new Rational(3,5);

            Rational expected = new Rational(4,5);

            // check both sum orders
            assertEquals(expected, r1.sum(r2));
            assertEquals(expected, r2.sum(r1));
        }

        @DisplayName("WHEN we compute the sum() of a positive and negative rational numbers with "
                + "the same denominator, THEN the correct rational number is returned.")
        @Test
        public void testPositiveNegativeCommonDenom() {
            // positive answer
            Rational r1 = new Rational(-1,5);
            Rational r2 = new Rational(3,5);

            Rational expected = new Rational(2,5);

            // check both sum orders
            assertEquals(expected, r1.sum(r2));
            assertEquals(expected, r2.sum(r1));

            // negative answer
            r1 = new Rational(1,5);
            r2 = new Rational(-3,5);

            expected = new Rational(-2,5);

            // check both sum orders
            assertEquals(expected, r1.sum(r2));
            assertEquals(expected, r2.sum(r1));
        }

        @DisplayName("WHEN we compute the sum() of two negative rational numbers with "
                + "the same denominator, THEN the correct rational number is returned.")
        @Test
        public void testNegativeCommonDenom() {
            Rational r1 = new Rational(-1,5);
            Rational r2 = new Rational(-3,5);

            Rational expected = new Rational(-4,5);

            // check both sum orders
            assertEquals(expected, r1.sum(r2));
            assertEquals(expected, r2.sum(r1));
        }

        @DisplayName("WHEN we compute the sum() of rational numbers that add to 0, THEN the correct "
                + "rational number (0) is returned.")
        @Test
        public void testSumToZero() {
            Rational r1 = new Rational(-3,5);
            Rational r2 = new Rational(3,5);

            Rational expected = new Rational(0,1);

            // check both sum orders
            assertEquals(expected, r1.sum(r2));
            assertEquals(expected, r2.sum(r1));
        }

        @DisplayName("WHEN we compute the sum() of two rational numbers with different denominators, "
                + "THEN the correct rational number is returned.")
        @Test
        public void testSumDifferentDenominators() {
            // sum denominator is bigger
            Rational r1 = new Rational(1,2);
            Rational r2 = new Rational(1,3);

            Rational expected = new Rational(5,6);

            // check both sum orders
            assertEquals(expected, r1.sum(r2));
            assertEquals(expected, r2.sum(r1));

            // sum denominator is smaller
            r1 = new Rational(5,6);
            r2 = new Rational(1,2);

            expected = new Rational(4,3);

            // check both sum orders
            assertEquals(expected, r1.sum(r2));
            assertEquals(expected, r2.sum(r1));
        }
    }

    @DisplayName("difference()")
    @Nested
    class DifferenceTest {

        @DisplayName("WHEN we compute the differnce() of two positive rational numbers with the "
                + "same denominator, THEN the correct rational number is returned.")
        @Test
        public void testPositiveCommonDenom() {
            Rational r1 = new Rational(1,5);
            Rational r2 = new Rational(3,5);
            assertEquals(new Rational(-2,5), r1.difference(r2));
            assertEquals(new Rational(2,5), r2.difference(r1));
        }

        @DisplayName("WHEN we compute the difference() of a positive and negative rational numbers "
                + "with the same denominator, THEN the correct rational number is returned.")
        @Test
        public void testPositiveNegativeCommonDenom() {
            Rational r1 = new Rational(-1,5);
            Rational r2 = new Rational(3,5);
            assertEquals(new Rational(-4,5), r1.difference(r2));
            assertEquals(new Rational(4,5), r2.difference(r1));
        }

        @DisplayName("WHEN we compute the difference() of two negative rational numbers with "
                + "the same denominator, THEN the correct rational number is returned.")
        @Test
        public void testNegativeCommonDenom() {
            Rational r1 = new Rational(-1,5);
            Rational r2 = new Rational(-3,5);
            assertEquals(new Rational(2,5), r1.difference(r2));
            assertEquals(new Rational(-2,5), r2.difference(r1));
        }

        @DisplayName("WHEN we compute the difference() of a rational numbers and itself, THEN the "
                + "correct rational number (0) is returned.")
        @Test
        public void testSumToZero() {
            Rational r = new Rational(3,5);
            assertEquals(new Rational(0,1), r.difference(r));
        }

        @DisplayName("WHEN we compute the sum() of two rational numbers with different denominators, "
                + "THEN the correct rational number is returned.")
        @Test
        public void testSumDifferentDenominators() {
            Rational r1 = new Rational(1,3);
            Rational r2 = new Rational(1,4);
            assertEquals(new Rational(1,12), r1.difference(r2));
            assertEquals(new Rational(-1,12), r2.difference(r1));
        }

    }

    @DisplayName("product()")
    @Nested
    class ProductTest {
        @DisplayName("WHEN we compute the product() two rational numbers, THEN this product has "
                + "the correct sign.")
        @Test
        public void testProductSign() {
            Rational p = new Rational(1,2);
            Rational n = new Rational(-1,2);

            assertTrue(p.product(p).numerator() > 0);
            assertTrue(p.product(n).numerator() < 0);
            assertTrue(n.product(p).numerator() < 0);
            assertTrue(n.product(n).numerator() > 0);
        }

        @DisplayName("WHEN we compute the product() of 0 and a nonzero rational numbers, THEN this "
                + "product is 0.")
        @Test
        public void testProductZero() {
            Rational r = new Rational(3,5);
            Rational z = new Rational(0,1);

            // check both orders
            assertEquals(z, z.product(r));
            assertEquals(z, r.product(z));
        }

        @DisplayName("The product of two rational numbers is computed correctly.")
        @Test
        public void testProductResult() {
            Rational r1 = new Rational(3,5);
            Rational r2 = new Rational(7,11);

            Rational expected = new Rational(21,55);

            // check both orders
            assertEquals(expected, r1.product(r2));
            assertEquals(expected, r2.product(r1));
        }

        @DisplayName("When the product() of two rational numbers can be simplified, THEN the resulting"
                + "rational number has the correct simplified form.")
        @Test
        public void testProductSimplified() {
            Rational r1 = new Rational(2,9);
            Rational r2 = new Rational(3,8);

            Rational expected = new Rational(1,12);

            // check both orders
            assertEquals(expected, r1.product(r2));
            assertEquals(expected, r2.product(r1));
        }
    }
}