import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab3.OptionsParsers;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab5.Stone;
import agh.cs.lab5.UnboundedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnboundedMapTest {
    public UnboundedMap map;


    @BeforeEach
    void setUp() {
        ArrayList<Stone> stones = new ArrayList<Stone>();
        stones.add(new Stone(new Vector2d(-4, -4)));
        stones.add(new Stone(new Vector2d(7, 7)));
        stones.add(new Stone(new Vector2d(3, 6)));
        stones.add(new Stone(new Vector2d(2, 0)));
        map = new UnboundedMap(stones);
        map.place(new Animal(map));
        map.place(new Animal(map, new Vector2d(3, 4)));
    }

    @Test
    void canMoveTo() {
        assertFalse(map.canMoveTo(new Vector2d(-4, -4)));
        assertFalse(map.canMoveTo(new Vector2d(7, 7)));
        assertFalse(map.canMoveTo(new Vector2d(3, 6)));
        assertFalse(map.canMoveTo(new Vector2d(2, 0)));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(3, 4)));
        assertTrue(map.canMoveTo(new Vector2d(100, 100)));
        assertTrue(map.canMoveTo(new Vector2d(-2, -2)));
        assertTrue(map.canMoveTo(new Vector2d(-2, -2)));
        assertTrue(map.canMoveTo(new Vector2d(7, -4)));
        assertTrue(map.canMoveTo(new Vector2d(0, 0)));
    }

    @Test
    void place() {
        assertTrue(map.place(new Animal(map, new Vector2d(1, 1))));
        assertTrue(map.place(new Animal(map, new Vector2d(6, 4))));
        assertFalse(map.place(new Animal(map, new Vector2d(2, 2))));
        assertFalse(map.place(new Animal(map, new Vector2d(3, 4))));
        assertFalse(map.place(new Animal(map, new Vector2d(-4, -4))));
        assertFalse(map.place(new Animal(map, new Vector2d(2, 0))));
        assertFalse(map.place(new Animal(map, new Vector2d(7, 7))));
        assertFalse(map.place(new Animal(map, new Vector2d(3, 6))));
        assertTrue(map.place(new Animal(map, new Vector2d(-10, -10))));
        assertTrue(map.place(new Animal(map, new Vector2d(100, 100))));
    }

    @Test
    void run() {
        MoveDirection[] directions = new OptionsParsers().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        map.run(directions);

        assertNotNull(map.objectAt(new Vector2d(7, 7)));
        assertNotNull(map.objectAt(new Vector2d(3, 5)));
        assertNotNull(map.objectAt(new Vector2d(2, 0)));
        assertNotNull(map.objectAt(new Vector2d(2, 1)));
        assertNotNull(map.objectAt(new Vector2d(-4, -4)));
        assertNull(map.objectAt(new Vector2d(7, 3)));
        assertNull(map.objectAt(new Vector2d(0, 0)));

        assertEquals(map.elements.get(4).getPosition(), new Vector2d(2, 1));
        assertEquals(map.elements.get(5).getPosition(), new Vector2d(3, 5));
        assertEquals(map.elements.get(4).toString(), "v");
        assertEquals(map.elements.get(5).toString(), "^");
    }

    @Test
    void isOccupied() {
        for (int i = -4; i <= 7; i++) {
            for (int j = -4; j <= 7; j++) {
                if ((i == 7 && j == 7) || (i == 3 && j == 6) || (i == 3 && j == 4 || (i == 2 && j == 2)) || (i == 2 && j == 0) || (i == -4 && j == -4))
                    assertTrue(map.isOccupied(new Vector2d(i, j)));
                else assertFalse(map.isOccupied(new Vector2d(i, j)));
            }
        }
        assertFalse(map.isOccupied(new Vector2d(100, 100)));
    }

    @Test
    void objectAt() {
        for (int i = -4; i <= 7; i++) {
            for (int j = -4; j <= 7; j++) {
                if ((i == 7 && j == 7) || (i == 3 && j == 6) || (i == 3 && j == 4 || (i == 2 && j == 2)) || (i == 2 && j == 0) || (i == -4 && j == -4))
                    assertNotNull(map.objectAt(new Vector2d(i, j)));
                else assertNull(map.objectAt(new Vector2d(i, j)));
            }
        }
        assertNull(map.objectAt(new Vector2d(100,100)));
    }
}
