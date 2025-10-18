package cs2110;

import java.util.MissingFormatArgumentException;
import java.util.Scanner;

public class ExpressionEvaluator {

    // TODO: Update these specs as you build out the functionality of the `evaluate()` method.

    /**
     * Evaluates the given well-formed mathematical expression `expr` and returns its value.
     * Currently, the `evaluate()` method supports: - Single-digit int literals - Addition -
     * Multiplication - Parentheses
     */
    public static int evaluate(String expr) throws MalformedExpressionException {
        Stack<Integer> operands = new LinkedStack<>();
        Stack<Character> operators = new LinkedStack<>(); // invariant: contains only '(', '+', and '*'

        boolean expectingOperator = false; // in infix notation, the first operand comes before an operator
        char[] expressionArray = expr.toCharArray();
        for (int i = 0; i < expressionArray.length; i++) {
            char c = expressionArray[i];
            if (c == '(') {
                if (expectingOperator) {
                    while (!operators.isEmpty() && operators.peek() == '*') {
                        oneStepSimplify(operands, operators);
                    }
                    operators.push('*');
                }
                operators.push('(');
                expectingOperator = false;
            } else if (c == '*') {
                if (!expectingOperator) {
                    throw new MalformedExpressionException(
                            "'*' must follow an operand, not an operator");
                }
                while (!operators.isEmpty() && operators.peek() == '*') {
                    oneStepSimplify(operands, operators);
                }
                operators.push('*');
                expectingOperator = false;
            } else if (c == '+') {
                if (!expectingOperator) {
                    throw new MalformedExpressionException(
                            "'+' must follow an operand, not an operator");
                }
                while (!operators.isEmpty() && (operators.peek() == '*' || operators.peek() == '+'
                        || operators.peek() == '-')) {
                    oneStepSimplify(operands, operators);
                }
                operators.push('+');
                expectingOperator = false;
            } else if (c == '-') {
                if (!expectingOperator) {
                    operands.push(0);
                    operators.push('-');
                } else {
                    while (!operators.isEmpty() && (operators.peek() == '*'
                            || operators.peek() == '+' || operators.peek() == '-')) {
                        oneStepSimplify(operands, operators);
                    }
                    operators.push('-');
                    expectingOperator = false;
                }
            } else if (c == ')') {
                if (!expectingOperator) {
                    throw new MalformedExpressionException(
                            "')' must follow an operand, not an operator");
                }
                if (operators.isEmpty()) {
                    throw new MalformedExpressionException("mismatched parentheses, extra ')'");
                }
                while (operators.peek() != '(') {
                    oneStepSimplify(operands, operators);
                    if (operators.isEmpty()) {
                        throw new MalformedExpressionException("mismatched parentheses, extra ')'");
                    }
                }
                operators.pop();
            } else if (Character.isWhitespace(c)) {
                int left = i - 1;
                while (left >= 0 && Character.isWhitespace(expressionArray[left])) {
                    left--;
                }
                int right = i + 1;
                while (right < expressionArray.length && Character.isWhitespace(
                        expressionArray[right])) {
                    right++;
                }
                if (left >= 0 && right < expressionArray.length && Character.isDigit(
                        expressionArray[left]) && Character.isDigit(expressionArray[right])) {
                    throw new MalformedExpressionException(
                            "whitespace inside a number is not allowed");
                }
                continue;
            } else { // c is a digit
                if (!(c >= '0' && c <= '9')) {
                    throw new MalformedExpressionException(
                            "expression contains an illegal character");
                }
                if (expectingOperator) {
                    while (!operators.isEmpty() && operators.peek() == '*') {
                        oneStepSimplify(operands, operators);
                    }
                    operators.push('*');
                }
                int index = i;
                StringBuilder fullNumber = new StringBuilder();
                while (index < expressionArray.length && Character.isDigit(
                        expressionArray[index])) {
                    fullNumber.append(expressionArray[index]);
                    index++;
                }
                operands.push(Integer.parseInt(fullNumber.toString()));
                expectingOperator = true;
                i = index - 1;
            }
        }
        if (!expectingOperator) {
            throw new MalformedExpressionException(
                    "expression must end with an operand, not an operator");
        }
        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                throw new MalformedExpressionException("mismatched parentheses, extra '('");
            }
            oneStepSimplify(operands, operators);
        }

        // If the above assertions pass, the operands stack should include exactly one value,
        // the return value. We'll include two assertions to verify this as a sanity check.
        if (operands.isEmpty()) {
            throw new MalformedExpressionException(
                    "operands shouldn't be empty, it should include the return value");
        }
        int result = operands.pop();
        if (!operands.isEmpty()) {
            throw new MalformedExpressionException(
                    "operands should now be empty because the return value has been popped");
        }
        return result;
    }

    /**
     * Helper method that partially simplifies the expression by `pop()`ping one operator from the
     * `operators` stack, `pop()`ping its two operands from the `operands` stack, evaluating the
     * operator, and then `push()`ing its result onto the `operands` stack. Requires that
     * `opererators.peek()` is '+' or '*' and `operands` includes at least two elements.
     */
    private static void oneStepSimplify(Stack<Integer> operands, Stack<Character> operators)
            throws MalformedExpressionException {
        char op = operators.pop();
        assert op == '+' || op == '-' || op == '*';

        int o2 = operands.pop(); // second operand is higher on stack
        int o1 = operands.pop();
        if (op == '+') {
            operands.push(o1 + o2);
        } else if (op == '-') {
            operands.push(o1 - o2);
        } else {
            operands.push(o1 * o2);
        }
    }


    /**
     * A very basic calculator application.
     */
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            while (true) { // repeat indefinitely
                System.out.print("Enter an expression, or enter \"q\" to quit: ");
                String expr = in.nextLine();
                if (expr.equals("q")) {
                    break; // exit loop
                }
                try {
                    System.out.println("= " + evaluate(expr));
                } catch (MalformedExpressionException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}
