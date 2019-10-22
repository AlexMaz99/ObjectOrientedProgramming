package agh.cs.lab2.test;
import agh.cs.lab2.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class Vector2dTest {
    @Test
    void equals(){
        Vector2d x = new Vector2d (1,2);
        assertTrue (x.equals(new Vector2d (1,2)));
        assertFalse (x.equals (new Vector2d (2,4)));
    }
    @Test
    void tooString(){
        assertEquals(new Vector2d(1,2).toString(), "(1,2)");
    }
    @Test
    void precedes(){
        assertTrue(new Vector2d(1,2).precedes(new Vector2d(4,5)));
    }
    @Test
    void follows(){
        assertTrue(new Vector2d(4,5).follows(new Vector2d(1,2)));
    }
    @Test
    void upperRight(){
        assertEquals(new Vector2d(4,5).upperRight(new Vector2d(1,10)).toString(),"(4,10)");
    }
    @Test
    void lowerLeft(){
        assertEquals(new Vector2d(4,5).lowerLeft(new Vector2d(1,10)).toString(),"(1,5)");
    }
    @Test
    void add(){
        assertEquals(new Vector2d(1,2).add(new Vector2d(4,5)).toString(), "(5,7)");
    }
    @Test
    void subtract(){
        assertEquals(new Vector2d(1,2).subtract(new Vector2d(4,5)).toString(), "(-3,-3)");
    }
    @Test
    void opposite(){
        assertEquals(new Vector2d(1,-2).opposite().toString(), "(-1,2)");
    }
}

