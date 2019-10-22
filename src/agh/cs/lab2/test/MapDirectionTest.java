package agh.cs.lab2.test;

import org.junit.jupiter.api.Test;


import static agh.cs.lab2.main.MapDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    void next() {
        assertEquals(EAST.next(), SOUTH);
        assertEquals(SOUTH.next(), WEST);
        assertEquals(WEST.next(), NORTH);
        assertEquals(NORTH.next(), EAST);
    }
    @Test
    void previous() {
        assertEquals(EAST.previous(), NORTH);
        assertEquals(NORTH.previous(), WEST);
        assertEquals(WEST.previous(), SOUTH);
        assertEquals(SOUTH.previous(), EAST);
    }
}
