package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EvaluatorTest {

    @DisplayName("WHEN we evaluate an expression containing only a single digit, THEN that digit is returned.")
    @Test
    public void testDigit() throws MalformedExpressionException {
        assertEquals(0, ExpressionEvaluator.evaluate("0"));
        assertEquals(1, ExpressionEvaluator.evaluate("1"));
        assertEquals(5, ExpressionEvaluator.evaluate("5"));
    }

    @DisplayName("WHEN we evaluate an expression containing only a single digit within parentheses, THEN that digit is returned.")
    @Test
    public void testParenthesizedDigit() throws MalformedExpressionException {
        assertEquals(0, ExpressionEvaluator.evaluate("(0)"));
        assertEquals(2, ExpressionEvaluator.evaluate("(2)"));
        assertEquals(4, ExpressionEvaluator.evaluate("((4))"));
    }

    @DisplayName("WHEN we evaluate an expression containing one addition operation applied to two single-digit operands, THEN the correct result is returned.")
    @Test
    public void testOneAddition() throws MalformedExpressionException {
        assertEquals(3, ExpressionEvaluator.evaluate("1+2"));
        assertEquals(11, ExpressionEvaluator.evaluate("4+7"));
        assertEquals(9, ExpressionEvaluator.evaluate("9+0"));
    }

    @DisplayName("WHEN we evaluate an expression containing one multiplication operation applied to two single-digit operands, THEN the correct result is returned.")
    @Test
    public void testOneMultiplication() throws MalformedExpressionException {
        assertEquals(2, ExpressionEvaluator.evaluate("1*2"));
        assertEquals(28, ExpressionEvaluator.evaluate("4*7"));
        assertEquals(0, ExpressionEvaluator.evaluate("9*0"));
    }

    @DisplayName("WHEN we evaluate an expression containing one addition operation applied to two single-digit operands with additional parentheses, THEN the correct result is returned.")
    @Test
    public void testOneOperatorParentheses() throws MalformedExpressionException {
        assertEquals(3, ExpressionEvaluator.evaluate("(1+2)"));
        assertEquals(3, ExpressionEvaluator.evaluate("(1)+2"));
        assertEquals(3, ExpressionEvaluator.evaluate("1+(2)"));
        assertEquals(3, ExpressionEvaluator.evaluate("(1)+(2)"));
        assertEquals(3, ExpressionEvaluator.evaluate("((1)+2)"));
        assertEquals(3, ExpressionEvaluator.evaluate("(1+(2))"));
        assertEquals(3, ExpressionEvaluator.evaluate("((1)+(2))"));
    }

    @DisplayName("WHEN an expression contains multiple of the same operator, THEN it is correctly evaluated.")
    @Test
    public void testOneOperatorMultipleTimes() throws MalformedExpressionException {
        assertEquals(6, ExpressionEvaluator.evaluate("1+2+3"));
        assertEquals(21, ExpressionEvaluator.evaluate("4+8+9"));
        assertEquals(28, ExpressionEvaluator.evaluate("1+2+3+4+5+6+7"));
        assertEquals(84, ExpressionEvaluator.evaluate("4*7*3"));
        assertEquals(180, ExpressionEvaluator.evaluate("5*6*2*3"));
    }

    @DisplayName("WHEN an expression contains both addition and multiplication but no parentheses, THEN the order of operations is respected.")
    @Test
    public void testBothOperators() throws MalformedExpressionException {
        assertEquals(7, ExpressionEvaluator.evaluate("1+2*3"));
        assertEquals(5, ExpressionEvaluator.evaluate("1*2+3"));
        assertEquals(15, ExpressionEvaluator.evaluate("1+2+3*4"));
        assertEquals(11, ExpressionEvaluator.evaluate("1+2*3+4"));
        assertEquals(14, ExpressionEvaluator.evaluate("1*2+3*4"));
        assertEquals(25, ExpressionEvaluator.evaluate("1+2*3*4"));
        assertEquals(10, ExpressionEvaluator.evaluate("1*2*3+4"));
    }

    @DisplayName("WHEN an expression contains both addition and multiplication and non-nested parentheses, THEN the order of operations is respected.")
    @Test
    public void testBothOperatorsParentheses() throws MalformedExpressionException {
        assertEquals(14, ExpressionEvaluator.evaluate("2+(3*4)"));
        assertEquals(20, ExpressionEvaluator.evaluate("(2+3)*4"));
        assertEquals(10, ExpressionEvaluator.evaluate("(2*3)+4"));
        assertEquals(14, ExpressionEvaluator.evaluate("2*(3+4)"));
        assertEquals(45, ExpressionEvaluator.evaluate("(2+3)*(4+5)"));
        assertEquals(70, ExpressionEvaluator.evaluate("2*(3+4)*5"));
    }

    @DisplayName("WHEN an expression contains both addition and multiplication and nested parentheses, THEN the order of operations is respected.")
    @Test
    public void testBothOperatorsNestedParentheses() throws MalformedExpressionException {
        assertEquals(94, ExpressionEvaluator.evaluate("2*(3+4*(5+6))"));
    }

    @DisplayName("WHEN evaluating expressions with multi-digit operands, THEN correct values are returned.")
    @Test
    public void testMultiDigitOperands() throws MalformedExpressionException {
        assertEquals(579, ExpressionEvaluator.evaluate("123+456"));
        assertEquals(25000, ExpressionEvaluator.evaluate("1000*25"));
        assertEquals(6336, ExpressionEvaluator.evaluate("66*96"));
    }

    @DisplayName("WHEN multi-digit and single-digit operands are mixed, THEN results are correct.")
    @Test
    public void testMixedMultiAndSingleDigit() throws MalformedExpressionException {
        assertEquals(123, ExpressionEvaluator.evaluate("2+3*40+1"));
        assertEquals(15, ExpressionEvaluator.evaluate("12+3"));
        assertEquals(68, ExpressionEvaluator.evaluate("12*3+4*8"));
    }

    @DisplayName("WHEN multi-digit operands are used with parentheses, THEN results are correct.")
    @Test
    public void testMultiDigitOperandsWithParentheses() throws MalformedExpressionException {
        assertEquals(579, ExpressionEvaluator.evaluate("(123+456)"));
        assertEquals(25000, ExpressionEvaluator.evaluate("(1000*25)"));
        assertEquals(6336, ExpressionEvaluator.evaluate("(66)*(96)"));
        assertEquals(6336, ExpressionEvaluator.evaluate("((66*96))"));
        assertEquals(579, ExpressionEvaluator.evaluate("((123)+(456))"));
    }

    @DisplayName("WHEN multi-digit and single-digit operands are mixed with parentheses, THEN results are correct.")
    @Test
    public void testMixedMultiAndSingleDigitWithParentheses() throws MalformedExpressionException {
        assertEquals(123, ExpressionEvaluator.evaluate("(2+(3*40)+1)"));
        assertEquals(15, ExpressionEvaluator.evaluate("((12)+3)"));
        assertEquals(68, ExpressionEvaluator.evaluate("(12*3)+(4*8)"));
        assertEquals(68, ExpressionEvaluator.evaluate("((12*3)+4*8)"));
        assertEquals(201, ExpressionEvaluator.evaluate("((2+3)*40)+1"));
    }

    @DisplayName("WHEN whitespace appears between operands and operators, THEN it is ignored.")
    @Test
    public void testWhitespaceAroundTokens() throws MalformedExpressionException {
        assertEquals(23, ExpressionEvaluator.evaluate("  3  +  4   *   5  "));
        assertEquals(60, ExpressionEvaluator.evaluate(" (  12  +  3 ) *  4   "));
        assertEquals(25000, ExpressionEvaluator.evaluate("  1000   *   25  "));
        assertEquals(579, ExpressionEvaluator.evaluate(" ( 123  + 456 ) "));
    }

    @DisplayName("WHEN whitespace appears between digits of the same number, THEN it throws a MalformedExpressionException.")
    @Test
    public void testWhitespaceInsideNumberIsError() {
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("1 23"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("12 3"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("(1 2)+3"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("4*(5  6)"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("  9  9  "));
    }

    @DisplayName("WHEN an expression mixes whitespace with operators and parentheses, THEN value is correct.")
    @Test
    public void testWhitespaceMixedStructure() throws MalformedExpressionException {
        assertEquals(123, ExpressionEvaluator.evaluate(" 2 +   3 * 40   + 1 "));
        assertEquals(201, ExpressionEvaluator.evaluate(" ( ( 2 + 3 )  *  40 ) + 1 "));
        assertEquals(68, ExpressionEvaluator.evaluate(" ( 12 * 3 )  +  ( 4 * 8 ) "));
    }

    @DisplayName("WHEN an expression is only whitespace or ends with an operator, THEN it is malformed.")
    @Test
    public void testOnlyWhitespaceOrTrailingOperatorIsError() {
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("   "));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("3 +  "));
    }

    @DisplayName("WHEN evaluating subtraction, THEN correct results are returned.")
    @Test
    public void testSimpleSubtraction() throws MalformedExpressionException {
        assertEquals(2, ExpressionEvaluator.evaluate("5-3"));
        assertEquals(0, ExpressionEvaluator.evaluate("3-3"));
        assertEquals(-4, ExpressionEvaluator.evaluate("1-5"));
    }

    @DisplayName("WHEN multiple subtractions are applied, THEN they are left-associative.")
    @Test
    public void testChainedSubtraction() throws MalformedExpressionException {
        assertEquals(7, ExpressionEvaluator.evaluate("10-2-1"));
        assertEquals(12, ExpressionEvaluator.evaluate("20-5-3"));
        assertEquals(85, ExpressionEvaluator.evaluate("100-10-5"));
    }

    @DisplayName("WHEN subtraction and addition are mixed, THEN left-associativity is still correct.")
    @Test
    public void testSubtractionAndAddition() throws MalformedExpressionException {
        assertEquals(9, ExpressionEvaluator.evaluate("10-2+1"));
        assertEquals(9, ExpressionEvaluator.evaluate("10+2-3"));
        assertEquals(4, ExpressionEvaluator.evaluate("5-3+4-2"));
    }

    @DisplayName("WHEN subtraction and multiplication are together, THEN multiplication has higher precedence.")
    @Test
    public void testSubtractionWithMultiplication() throws MalformedExpressionException {
        assertEquals(4, ExpressionEvaluator.evaluate("10-2*3"));
        assertEquals(17, ExpressionEvaluator.evaluate("10*2-3"));
        assertEquals(5, ExpressionEvaluator.evaluate("10-2*3+1"));
        assertEquals(-14, ExpressionEvaluator.evaluate("10-2*3*4"));
    }

    @DisplayName("WHEN subtraction is combined with parentheses, THEN parentheses change the normal precedence if necessary.")
    @Test
    public void testSubtractionWithParentheses() throws MalformedExpressionException {
        assertEquals(7, ExpressionEvaluator.evaluate("10-(2+1)"));
        assertEquals(24, ExpressionEvaluator.evaluate("(10-2)*3"));
        assertEquals(-10, ExpressionEvaluator.evaluate("10-((2+3)*4)"));
        assertEquals(10, ExpressionEvaluator.evaluate("((10-2)+(3-1))"));
    }

    @DisplayName("WHEN multi-digit numbers and subtraction are combined, THEN results are correct.")
    @Test
    public void testMultiDigitWithSubtraction() throws MalformedExpressionException {
        assertEquals(77, ExpressionEvaluator.evaluate("100-23"));
        assertEquals(8, ExpressionEvaluator.evaluate("(20-6)-(4+2)"));
        assertEquals(579, ExpressionEvaluator.evaluate("1234-655"));
    }

    @DisplayName("WHEN unary negation appears before a number or parenthesized expression, THEN it negates the value.")
    @Test
    public void testUnaryNegationBasics() throws MalformedExpressionException {
        assertEquals(-5, ExpressionEvaluator.evaluate("-5"));
        assertEquals(-5, ExpressionEvaluator.evaluate("-(3+2)"));
        assertEquals(-5, ExpressionEvaluator.evaluate("---5"));
        assertEquals(5, ExpressionEvaluator.evaluate("--5"));
    }

    @DisplayName("WHEN unary negation is combined with multiplication, THEN precedence and sign changes work correctly.")
    @Test
    public void testUnaryNegationWithMultiplication() throws MalformedExpressionException {
        assertEquals(-5, ExpressionEvaluator.evaluate("5*-1"));
        assertEquals(-24, ExpressionEvaluator.evaluate("2*-3*4"));
        assertEquals(24, ExpressionEvaluator.evaluate("-2*-3*4"));
        assertEquals(-5, ExpressionEvaluator.evaluate("-(2*3)+1"));
    }

    @DisplayName("WHEN unary negation mixes with addition and/or subtraction, THEN results are correct.")
    @Test
    public void testUnaryNegationWithAddSub() throws MalformedExpressionException {
        assertEquals(3, ExpressionEvaluator.evaluate("5+-2"));
        assertEquals(-7, ExpressionEvaluator.evaluate("-2-5"));
        assertEquals(11, ExpressionEvaluator.evaluate("20+-(3*3)"));
        assertEquals(7, ExpressionEvaluator.evaluate("5+--2"));
        assertEquals(7, ExpressionEvaluator.evaluate("5-   -   2"));
        assertEquals(7, ExpressionEvaluator.evaluate("- - 7"));
    }

    @DisplayName("WHEN implicit multiplication occurs, THEN results are correct.")
    @Test
    public void testImplicitMultiplication() throws MalformedExpressionException {
        assertEquals(12, ExpressionEvaluator.evaluate("(3)(4)"));
        assertEquals(25, ExpressionEvaluator.evaluate("(2+3)(4+1)"));
        assertEquals(6, ExpressionEvaluator.evaluate("((1+1))(3)"));
        assertEquals(25, ExpressionEvaluator.evaluate("5(2+3)"));
        assertEquals(31, ExpressionEvaluator.evaluate("3(4+5) + 2(1+1)"));
        assertEquals(9, ExpressionEvaluator.evaluate("(1+2)3"));
        assertEquals(-6, ExpressionEvaluator.evaluate("(-2)3"));
        assertEquals(2, ExpressionEvaluator.evaluate("10-2(3+1)"));
        assertEquals(24, ExpressionEvaluator.evaluate("2(3)(4)"));
        assertEquals(24, ExpressionEvaluator.evaluate("002(3)(4)"));
    }

    @DisplayName("WHEN expressions are malformed in various ways, THEN an exception is thrown.")
    @Test
    public void testMalformedExpressions() throws MalformedExpressionException {
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("+5+7"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("5 7"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("-5 7"));
        assertThrows(MalformedExpressionException.class, () -> ExpressionEvaluator.evaluate("+57"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("+5+7"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5-7-"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5+7)"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("(5+7"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5+*7"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5***7"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("+34+7"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5 765"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("-5 222"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("+545"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("+512+721"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5-72-"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5+7323)"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("(5+732"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5555+*5555"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("512***712"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("1+1+d"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("!"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("+6"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("3(++)6"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("3++5"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("348349-+213"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("5+4-"));
        assertThrows(MalformedExpressionException.class,
                () -> ExpressionEvaluator.evaluate("3=3"));
    }
}