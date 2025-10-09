package cs2110;

/**
 * Models a rational number, represented as the quotient of two `int`s in
 * simplified form. To keep things simple for this discussion, we ignore the
 * possibility of overflow by assuming that the numerators and denominators of
 * these fractions are sufficiently small.
 */
public class Rational {

    /**
     * The numerator of the simplified representation of this rational number. Must have
     * gcd(num,denom) = 1.
     */
    private final int num;

    /**
     * The denominator of the simplified representation of this rational number. Must have
     * gcd(num,denom) = 1 and denom > 0.
     */
    private final int denom;

    private int gcd(int m, int n) {
        if (n % m == 0) return m;
        return gcd(n%m, m);
    }
    /**
     * Constructs a new rational number representing the value `num/denom`.
     * Requires that `denom != 0`.
     */
    public Rational(int num, int denom) {
        assert denom != 0;

        // TODO 1.1: Complete the definition of this constructor so that it initializes the fields
        //  in a way that satisfies both the specification and the class invariant.
        if (denom < 0) {
            num = -num;
            denom = -denom;
        }
        if (num == 0 && denom != 0) {
            this.num = 0;
            this.denom = 1;
        }
        else {
            int g = gcd(num, denom);
            this.num = num / g;
            this.denom = denom / g;
        }
    }

    /**
     * Returns the numerator of the simplified representation of this rational number.
     */
    public int numerator() {
        return num;
    }

    /**
     * Returns the denominator of the simplified representation of this rational number.
     */
    public int denominator() {
        return denom;
    }

    /**
     * Returns a `String` representing this number in the form "<num>/<denom>" where <num> and
     * <denom> are the numerator and denominator (respectively) of its simplified fraction value.
     */
    @Override
    public String toString() {
        return num + "/" + denom;
    }

    @Override
    public boolean equals(Object other) {
        // TODO 1.3: Implement this method according to its specifications (inherited from `Object`).
        //  Follow the `equals()` method template given in the lecture notes.
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        Rational rational = (Rational) other;
        return (this.numerator() == rational.numerator() && this.denominator() == rational.denominator());
    }

    /**
     * Constructs and returns a new `Rational` object whose value is the sum of this rational
     * number and the given `other` rational number.
     */
    public Rational sum(Rational other) {
        // TODO 1.4a: Implement this method according to its specifications.
        int newNum = this.num * other.denom + other.num * this.denom;
        int newDen = this.denom * other.denom;
        return new Rational(newNum, newDen);
    }

    /**
     * Constructs and returns a new `Rational` object whose value is this rational number minus the
     * given `other` rational number.
     */
    public Rational difference(Rational other) {
        int newNum = this.num * other.denom - other.num * this.denom;
        int newDen = this.denom * other.denom;
        return new Rational(newNum, newDen);
    }

    /**
     * Constructs and returns a new `Rational` object whose value is the product of this rational
     * number and the given `other` rational number.
     */
    public Rational product(Rational other) {
        // TODO 1.4c: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }
}