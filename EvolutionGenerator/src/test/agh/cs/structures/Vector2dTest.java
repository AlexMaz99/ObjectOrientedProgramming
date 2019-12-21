package agh.cs.structures;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    void equals(){
        Vector2d x = new Vector2d (1,2);
        assertTrue (x.equals(new Vector2d (1,2)));
        assertFalse (x.equals (new Vector2d (2,4)));

        Vector2d y = new Vector2d (5,10);
        assertTrue (y.equals(new Vector2d(5,10)));
        assertFalse (y.equals(x));
    }
    @Test
    void tooString(){
        assertEquals(new Vector2d(1,2).toString(), "(1,2)");
        assertEquals(new Vector2d(-1,-2).toString(), "(-1,-2)");
        assertEquals(new Vector2d(5,-10).toString(), "(5,-10)");
        assertEquals(new Vector2d(-20,100).toString(), "(-20,100)");
    }
    @Test
    void precedes(){
        assertTrue(new Vector2d(1,2).precedes(new Vector2d(4,5)));
        assertTrue(new Vector2d(-1,-2).precedes(new Vector2d(1,-2)));
        assertTrue(new Vector2d(1,2).precedes(new Vector2d(1,2)));
        assertTrue(new Vector2d(5,10).precedes(new Vector2d(5,20)));
        assertFalse(new Vector2d(5,10).precedes(new Vector2d(1,2)));
        assertFalse(new Vector2d(4,5).precedes(new Vector2d(-1,5)));
        assertFalse(new Vector2d(6,8).precedes(new Vector2d(6,1)));
        assertFalse(new Vector2d(10,10).precedes(new Vector2d(1,10)));
    }
    @Test
    void follows(){
        assertFalse(new Vector2d(1,2).follows(new Vector2d(4,5)));
        assertFalse(new Vector2d(-1,-2).follows(new Vector2d(1,-2)));
        assertFalse(new Vector2d(5,10).follows(new Vector2d(5,20)));
        assertFalse(new Vector2d(5,10).follows(new Vector2d(15,10)));
        assertTrue(new Vector2d(4,5).follows(new Vector2d(-1,5)));
        assertTrue(new Vector2d(6,8).follows(new Vector2d(6,1)));
        assertTrue(new Vector2d(10,10).follows(new Vector2d(1,10)));
        assertTrue(new Vector2d(1,2).follows(new Vector2d(1,2)));
    }
    @Test
    void upperRight(){
        assertEquals(new Vector2d(4,5).upperRight(new Vector2d(1,10)).toString(),"(4,10)");
        assertEquals(new Vector2d(2,-1).upperRight(new Vector2d(5,-6)).toString(),"(5,-1)");
        assertEquals(new Vector2d(-6,-8).upperRight(new Vector2d(-10,-1)).toString(),"(-6,-1)");
        assertEquals(new Vector2d(15,2).upperRight(new Vector2d(-5,10)).toString(),"(15,10)");
        assertEquals(new Vector2d(-5,8).upperRight(new Vector2d(-3,12)).toString(),"(-3,12)");
    }
    @Test
    void lowerLeft(){
        assertEquals(new Vector2d(4,5).lowerLeft(new Vector2d(1,10)).toString(),"(1,5)");
        assertEquals(new Vector2d(2,-1).lowerLeft(new Vector2d(5,-6)).toString(),"(2,-6)");
        assertEquals(new Vector2d(-6,-8).lowerLeft(new Vector2d(-10,-1)).toString(),"(-10,-8)");
        assertEquals(new Vector2d(15,2).lowerLeft(new Vector2d(-5,10)).toString(),"(-5,2)");
        assertEquals(new Vector2d(-5,8).lowerLeft(new Vector2d(-3,12)).toString(),"(-5,8)");
    }
    @Test
    void add(){
        assertEquals(new Vector2d(1,2).add(new Vector2d(4,5)).toString(), "(5,7)");
        assertEquals(new Vector2d(-1,-2).add(new Vector2d(4,5)).toString(), "(3,3)");
        assertEquals(new Vector2d(-3,-5).add(new Vector2d(-6,-2)).toString(), "(-9,-7)");
        assertEquals(new Vector2d(10,-4).add(new Vector2d(-6,5)).toString(), "(4,1)");
    }
    @Test
    void subtract(){
        assertEquals(new Vector2d(1,2).subtract(new Vector2d(4,5)).toString(), "(-3,-3)");
        assertEquals(new Vector2d(5,10).subtract(new Vector2d(-2,-6)).toString(), "(7,16)");
        assertEquals(new Vector2d(-6,-3).subtract(new Vector2d(-1,0)).toString(), "(-5,-3)");
        assertEquals(new Vector2d(-5,-9).subtract(new Vector2d(5,1)).toString(), "(-10,-10)");
    }
    @Test
    void opposite(){
        assertEquals(new Vector2d(1,-2).opposite().toString(), "(-1,2)");
        assertEquals(new Vector2d(0,0).opposite().toString(), "(0,0)");
        assertEquals(new Vector2d(-3,-5).opposite().toString(), "(3,5)");
        assertEquals(new Vector2d(-10,8).opposite().toString(), "(10,-8)");
    }
}
