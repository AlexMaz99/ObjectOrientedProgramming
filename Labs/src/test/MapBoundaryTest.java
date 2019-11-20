import agh.cs.lab2.Vector2d;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class MapBoundaryTest {

    private Comparator <Vector2d> xComparator = Comparator.comparing(Vector2d::getX).thenComparing(Vector2d::getY);
    private Comparator <Vector2d> yComparator = Comparator.comparing(Vector2d::getY).thenComparing(Vector2d::getX);
    private TreeSet<Vector2d> xElements = new TreeSet<>(xComparator);
    private TreeSet<Vector2d> yElements = new TreeSet<>(yComparator);

    @Test
    void addObject(){
        xElements.add(new Vector2d(1,1));
        xElements.add(new Vector2d(6,6));
        xElements.add(new Vector2d(5,5));

        yElements.add(new Vector2d(1,1));
        yElements.add(new Vector2d(6,6));
        yElements.add(new Vector2d(5,5));

        assertEquals(xElements.first(), new Vector2d (1,1));
        assertEquals(xElements.last(), new Vector2d (6,6));
        assertEquals(yElements.first(), new Vector2d (1,1));
        assertEquals(yElements.last(), new Vector2d (6,6));

        xElements.add(new Vector2d(1,2));
        xElements.add(new Vector2d(7,6));
        xElements.add(new Vector2d(5,10));

        yElements.add(new Vector2d(1,2));
        yElements.add(new Vector2d(7,6));
        yElements.add(new Vector2d(5,10));

        assertEquals(xElements.first(), new Vector2d (1,1));
        assertEquals(xElements.last(), new Vector2d (7,6));
        assertEquals(yElements.first(), new Vector2d (1,1));
        assertEquals(yElements.last(), new Vector2d (5,10));

        assertTrue(xElements.contains(new Vector2d(1,2)));
        assertTrue(xElements.contains(new Vector2d(5,5)));
        assertTrue(yElements.contains(new Vector2d(1,2)));
        assertTrue(yElements.contains(new Vector2d(5,5)));
    }

    @Test
    void deleteObject(){
        xElements.add(new Vector2d(1,2));
        xElements.add(new Vector2d(6,10));
        xElements.add(new Vector2d(5,5));
        xElements.add(new Vector2d(4,12));

        yElements.add(new Vector2d(1,2));
        yElements.add(new Vector2d(6,10));
        yElements.add(new Vector2d(5,5));
        yElements.add(new Vector2d(4,12));

        assertEquals(xElements.first(), new Vector2d (1,2));
        assertEquals(xElements.last(), new Vector2d (6,10));
        assertEquals(yElements.first(), new Vector2d (1,2));
        assertEquals(yElements.last(), new Vector2d (4,12));

        xElements.remove(new Vector2d(1,2));
        yElements.remove(new Vector2d(1,2));
        assertEquals(xElements.first(), new Vector2d (4,12));
        assertEquals(yElements.first(), new Vector2d (5,5));

        xElements.remove(new Vector2d(4,12));
        yElements.remove(new Vector2d(4,12));
        assertEquals(xElements.first(), new Vector2d (5,5));
        assertEquals(yElements.last(), new Vector2d (6,10));

        assertFalse(xElements.contains(new Vector2d(1,2)));
        assertFalse(xElements.contains(new Vector2d(4,12)));
        assertFalse(yElements.contains(new Vector2d(1,2)));
        assertFalse(yElements.contains(new Vector2d(4,12)));
    }
}
