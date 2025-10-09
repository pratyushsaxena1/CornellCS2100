package cs2110;

public class Practice {

    /**
     * Returns the median of the three given integers, `a`, `b`, and `c`. A number `m` is the
     * "median of 3" if there is at least one other number that is <= `m` and at least one other
     * number that is >= `m`.
     */
    static int med3(int a, int b, int c) {
        // TODO 1: Use `if`-`else` statements to complete the definition of this method.
        if (a <= b && b <= c) {
            return b;
        }
        else if (c <= b && b <= a) {
            return b;
        }
        else if (a <= c && c <= b) {
            return c;
        }
        else if (b <= c && c <= a) {
            return c;
        }
        else if (b <= a && a <= c) {
            return a;
        }
        else if (c <= a && a <= b) {
            return a;
        }
        else {
            return -1;
        }
    }

    /**
     * Returns the greatest common divisor of `m` and `n`. Requires that `0 < m <= n`.
     */
    static int gcdLoop(int m, int n) {
        assert 0 < m && m <= n;

        // TODO 2: Compute the gcd using a `for`-loop to iterate over the values of
        //  {1,...,m}, checking which are divisors of both m and n.
        int curr_gcd = 1;
        for (int i = 1; i <= m; i++) {
            double first = (double) m / (double) i;
            double second = (double) n / (double) i;
            if (first == (int) first && second == (int) second) {
                curr_gcd = i;
            }
        }
        return curr_gcd;
    }

    /**
     * Returns the greatest common divisor of `m` and `n`. Requires that `0 < m <= n`.
     */
    static int gcdEuclideanIterative(int m, int n) {
        assert 0 < m && m <= n;

        // TODO 3: Compute the gcd using a `while`-loop to instantiate the Euclidean algorithm:
        //  If m divides n, then gcd{m,n} = m. Otherwise, gcd{m,n} = gcd{m,n % m}.
        int updating_n = n;
        while (updating_n % m != 0 && m % updating_n != 0) {
            if (updating_n % m == 0 || m % updating_n == 0) {
                return m;
            }
            else {
                updating_n = n % m;
            }
        }
        return 1;
    }

    /**
     * Returns the greatest common divisor of `m` and `n`. Requires that `0 < m <= n`.
     */
    static int gcdEuclideanRecursive(int m, int n) {
        assert 0 < m && m <= n;

        // TODO 4: Compute the gcd recursively using the Euclidean algorithm:
        //  If m divides n, then gcd{m,n} = m. Otherwise, gcd{m,n} = gcd{m,n % m}.
        throw new UnsupportedOperationException();
    }

    /**
     * Prints the outputs of a game of FizzBuzz on inputs 1,...,n (inclusive). A number is a "Fizz"
     * number if it is divisible by 3. A number is a "Buzz" number if it is divisible by 5. If a
     * number is a "Fizz" number and a "Buzz" number, "FizzBuzz" is printed. If a number is only a
     * "Fizz" number, "Fizz" is printed. If a number is only a "Buzz" number, "Buzz" is printed.
     * Otherwise, the number itself is printed. Each of the n outputs should be printed on a
     * separate line (use `System.out.println()`). Requires `n >= 1`.
     */
    static void fizzBuzz(int n) {
        assert n >= 1;

        // TODO 5: Complete this method according to its specification.
        throw new UnsupportedOperationException();
    }

    /**
     * Prints the outputs of a game of FizzBuzz on inputs 1,...,n (inclusive) in the same manner as
     * the `fizzBuzz()` method, except now: A number is a "Fizz" number if it is divisible by 3 *or*
     * has a 3 in its base-10 representation. A number is a "Buzz" number if it is divisible by 5
     * *or* has a 5 in its base-10 representation.
     */
    static void fizzBuzzHard(int n) {
        assert n >= 1;

        // TODO 6: Complete this method according to its specification.
        //  It may be helpful to write some *helper methods* to separate out some computation
        //  and make your logic easier to follow.
        throw new UnsupportedOperationException();
    }
}
