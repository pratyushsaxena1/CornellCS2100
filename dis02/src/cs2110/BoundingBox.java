package cs2110;

/**
 * An immutable, axis-aligned, closed rectangle in the 2D coordinate plane.
 */
public class BoundingBox {

    /**
     * The upper left corner point of this bounding box. Is null only when this bounding box is
     * empty.
     */
    private final Point ulCorner;

    /**
     * The width of this bounding box. Must be 0 if this bounding box is empty.
     */
    private final double width;

    /**
     * The height of this bounding box. Must be 0 if this bounding box is empty.
     */
    private final double height;

    /**
     * Constructs an empty bounding box that contains no points.
     */
    public BoundingBox() {
        ulCorner = null;
        width = 0;
        height = 0;
    }

    /**
     * Constructs a (non-empty) bounding box with the given upper left `corner`, `width` and
     * `height`. Requires that `corner != null`, `width >= 0`, and `height >= 0`.
     */
    public BoundingBox(Point corner, double width, double height) {
        assert corner != null;
        assert width >= 0;
        assert height >= 0;

        ulCorner = corner;
        this.width = width;
        this.height = height;
    }


    /**
     * Returns the upper left corner this bounding box, or returns null if this bounding box is
     * empty.
     */
    public Point upperLeftCorner() {
        return ulCorner;
    }

    /**
     * Returns the width of this bounding box (in units of the coordinate system). Returns 0 if this
     * bounding box is empty.
     */
    public double width() {
        return width;
    }

    /**
     * Returns the height of this bounding box (in units of the coordinate system). Returns 0 if
     * this bounding box is empty.
     */
    public double height() {
        return height;
    }

    /**
     * Returns the area of this bounding box (in squared units of the coordinate system).
     */
    public double area() {
        return width * height;
    }

    /**
     * Returns `true` if this bounding box is empty, so contains no points, otherwise returns
     * `false`.
     */
    public boolean isEmpty() {
        return ulCorner == null;
    }

    /**
     * Returns whether the given point `p` is contained within this bounding box, either in its
     * *interior* or incident to one of its edges or corners.
     */
    public boolean containsPoint(Point p) {
        if (isEmpty()) {
            return false;
        }
        assert ulCorner != null;
        return ulCorner.x() <= p.x() && p.x() <= ulCorner.x() + width
                && p.y() <= ulCorner.y() && ulCorner.y() - height <= p.y();
    }

    /**
     * Returns whether every point of the given bounding box `b` is contained within this bounding
     * box.
     */
    public boolean containsBox(BoundingBox b) {
        if (b.isEmpty()) {
            return true;
        }
        assert b.ulCorner != null;
        Point lrCorner = new Point(b.ulCorner.x() + b.width, b.ulCorner.y() - b.height);
        return containsPoint(b.ulCorner) && containsPoint(lrCorner);
    }

    /**
     * Returns the intersection of this bounding box with the given bounding box `b`.
     */
    public BoundingBox intersectWith(BoundingBox b) {
        if (isEmpty() || b.isEmpty()) {
            return new BoundingBox();
        }
        assert ulCorner != null;
        assert b.ulCorner != null;

        // make sure there is x overlap
        if (ulCorner.x() + width < b.ulCorner.x() || b.ulCorner.x() + b.width < ulCorner.x()) {
            return new BoundingBox();
        }

        // make sure there is y overlap
        if (ulCorner.y() < b.ulCorner.y() - b.height || b.ulCorner.y() < ulCorner.y() - height) {
            return new BoundingBox();
        }

        double xMin = Math.max(ulCorner.x(), b.ulCorner.x());
        double xMax = Math.min(ulCorner.x() + width, b.ulCorner.x() + b.width);
        double yMin = Math.max(ulCorner.y() - height, b.ulCorner.y() - b.height);
        double yMax = Math.min(ulCorner.y(), b.ulCorner.y());
        return new BoundingBox(new Point(xMin, yMax), xMax - xMin, yMax - yMin);
    }

    /**
     * Returns whether this BoundingBox is equal to another, which is true when they have the same
     * dimensions and upper left corners.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        BoundingBox otherBox = (BoundingBox) other;

        if (ulCorner == null) {
            return otherBox.ulCorner == null;
        }
        return width == otherBox.width && height == otherBox.height && ulCorner.equals(
                otherBox.ulCorner);
    }

    // Note: Since we overrode `equals()` we should also override `hashCode()`. We'll discuss this
    // later in the course.
}
