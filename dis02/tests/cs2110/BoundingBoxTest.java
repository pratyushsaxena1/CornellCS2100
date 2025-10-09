package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundingBoxTest {

    @DisplayName("WHEN we construct an empty bounding box, THEN it has a `null` upper left corner "
            + "and width, height, and area all 0.")
    @Test
    void testEmptyConstruction() {
        BoundingBox emptyBox = new BoundingBox();
        assertNull(emptyBox.upperLeftCorner());
        assertEquals(0, emptyBox.width(), "incorrect box width");
        assertEquals(0, emptyBox.height(), "incorrect box height");
        assertEquals(0, emptyBox.area(), "incorrect box area");
    }

    @DisplayName("WHEN we construct a non-empty bounding box AND a point in its interior, THEN the "
            + "box should contain that point.")
    @Test
    void testContainsInteriorPoint() {
        BoundingBox box = new BoundingBox(new Point(0, 2), 2, 2);
        Point interiorPoint = new Point(1, 1);
        assertTrue(box.containsPoint(interiorPoint));
    }

    @DisplayName("WHEN the intersection of two bounding box is another 2D bounding box, THEN "
            + "this intersection should have the correct location and dimensions.")
    @Test
    void test2DIntersection() {
        // this is the test case from the handout figure
        BoundingBox redBox = new BoundingBox(new Point(2, 5), 3, 3);
        BoundingBox blueBox = new BoundingBox(new Point(4, 4), 3, 3);

        BoundingBox purpleBox = redBox.intersectWith(blueBox);
        assertEquals(new Point(4, 4), purpleBox.upperLeftCorner(),
                "incorrect upper left corner of intersection");
        assertEquals(1, purpleBox.width(), "incorrect intersection width");
        assertEquals(2, purpleBox.height(), "incorrect intersection height");

        // check that the other intersection order also works
        purpleBox = blueBox.intersectWith(redBox);
        assertEquals(new Point(4, 4), purpleBox.upperLeftCorner(),
                "incorrect upper left corner of intersection");
        assertEquals(1, purpleBox.width(), "incorrect intersection width");
        assertEquals(2, purpleBox.height(), "incorrect intersection height");
    }

    // TODO: Add more tests to fully cover the `BoundingBox` specifications.
    @DisplayName("WHEN we construct a bounding box, THEN it does not have a `null` upper left corner "
            + "and width, height, and area are all greater than 0.")
    @Test
    void testProperConstruction() {
        BoundingBox normalBox = new BoundingBox(new Point(0, 0), 1, 1);
        assertNotNull(normalBox.upperLeftCorner());
        assertNotEquals(0, normalBox.width(), "incorrect box width");
        assertNotEquals(0, normalBox.height(), "incorrect box height");
        assertNotEquals(0, normalBox.area(), "incorrect box area");
    }
    @DisplayName("WHEN we construct a point, THEN it does not equal a box with the same upper left corner.")
    @Test
    void testPointIsNotBox() {
        Point normalPoint = new Point(1, 2);
        BoundingBox normalBox = new BoundingBox(new Point(1, 2), 1, 1);
        assertFalse(normalPoint.equals(normalBox));
    }
    @DisplayName("WHEN we construct a box, THEN it should return the correct upper left corner, width, height, and area.")
    @Test
    void testReturnValues() {
        BoundingBox normalBox = new BoundingBox(new Point(1, 2), 2, 8);
        assert normalBox.upperLeftCorner().equals(new Point(1, 2));
        assert normalBox.width() == 2;
        assert normalBox.height() == 8;
        assert normalBox.area() == 16;
    }
    @DisplayName("WHEN we construct a box with 0 width, THEN it should return the correct upper left corner and area.")
    @Test
    void testZeroWidth() {
        BoundingBox normalBox = new BoundingBox(new Point(3, 3), 0, 8);
        assert normalBox.upperLeftCorner().equals(new Point(3, 3));
        assert normalBox.area() == 0;
    }
    @DisplayName("WHEN we construct a box with 0 height, THEN it should return the correct upper left corner and area.")
    @Test
    void testZeroHeight() {
        BoundingBox normalBox = new BoundingBox(new Point(3, 3), 5, 0);
        assert normalBox.upperLeftCorner().equals(new Point(3, 3));
        assert normalBox.area() == 0;
    }

}
