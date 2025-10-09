package cs2110;

/**
 * An immutable point with finite coordinates in the 2D coordinate plane.
 */
public class Point {

    /**
     * The x-coordinate of this point. Must be finite.
     */
    private final double x;

    /**
     * The y-coordinate of this point. Must be finite.
     */
    private final double y;

    /**
     * Constructs a point with given coordinates (x,y). Requires that x and y are finite.
     */
    public Point(double x, double y) {
        assert Double.isFinite(x);
        assert Double.isFinite(y);
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this point.
     */
    public double x() {
        return x;
    }

    /**
     * Returns the y-coordinate of this point.
     */
    public double y() {
        return y;
    }

    /**
     * Returns a String representation of this point's coordinates, which are surrounded by
     * parentheses and separated by a comma.
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Returns whether this Point is equal to another, which is true when the points have the same
     * coordinates.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Point otherPoint = (Point) other;
        return x == otherPoint.x && y == otherPoint.y;
    }

    // Note: Since we overrode `equals()` we should also override `hashCode()`. We'll discuss this
    // later in the course.
}
