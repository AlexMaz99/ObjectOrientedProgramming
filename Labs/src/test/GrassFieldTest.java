import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab3.OptionsParsers;
import agh.cs.lab5.AbstractWorldMap;
import agh.cs.lab5.Grass;
import agh.cs.lab5.GrassField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassFieldTest {
    private AbstractWorldMap map;
    private List<Animal> animals = new ArrayList<>();


    @BeforeEach
    void setUp() {
        map = new GrassField(10);
        map.place(new Animal(map));
        map.place(new Animal(map, new Vector2d(3, 4)));
    }

    @Test
    void canMoveTo() {
        assertTrue(map.canMoveTo(new Vector2d(-4, -4)));
        assertTrue(map.canMoveTo(new Vector2d(100, 100)));
        assertTrue(map.canMoveTo(new Vector2d(0, 0)));

        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                if ((i == 2 && j == 2) || (i == 3 && j == 4))
                    assertFalse(map.canMoveTo(new Vector2d(i, j)));
                else
                    assertTrue(map.canMoveTo(new Vector2d(i, j)));
            }
        }
        map.place(new Animal (map, new Vector2d(10,10)));
        assertFalse(map.canMoveTo(new Vector2d (10,10)));
    }

    @Test
    void place() {
        assertTrue(map.place(new Animal(map, new Vector2d(1, 1))));
        assertTrue(map.place(new Animal(map, new Vector2d(6, 4))));

        assertThrows(IllegalArgumentException.class, () -> {
                    map.place(new Animal(map, new Vector2d(2, 2)));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            map.place(new Animal(map, new Vector2d(3, 4)));
        });


        assertTrue(map.place(new Animal(map, new Vector2d(-4, -4))));
        assertTrue(map.place(new Animal(map, new Vector2d(2, 0))));
        assertTrue(map.place(new Animal(map, new Vector2d(7, 7))));
        assertTrue(map.place(new Animal(map, new Vector2d(3, 6))));
        assertTrue(map.place(new Animal(map, new Vector2d(-10, -10))));
        assertTrue(map.place(new Animal(map, new Vector2d(100, 100))));
    }

    @Test
    void run() {
        MoveDirection[] directions = new OptionsParsers().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        map.run(directions);

        assertNotNull(map.objectAt(new Vector2d(2, -1)));
        assertNotNull(map.objectAt(new Vector2d(3, 7)));
        assertNull(map.objectAt(new Vector2d(100,100)));

        animals = map.getAnimals();
        assertEquals(animals.get(0).getPosition(), new Vector2d(2, -1));
        assertEquals(animals.get(1).getPosition(), new Vector2d(3, 7));
        assertEquals(animals.get(0).toString(), "v");
        assertEquals(animals.get(1).toString(), "^");
    }

    @Test
    void isOccupied() {
        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(3, 4)));
        assertFalse(map.isOccupied(new Vector2d(100, 100)));
        assertFalse(map.isOccupied(new Vector2d(-100, -100)));

        if (map.objectAt(new Vector2d(5,5)) == null)
            assertFalse(map.isOccupied(new Vector2d(5,5)));
        else assertTrue (map.isOccupied(new Vector2d(5,5)));

        if (map.objectAt(new Vector2d(2,3)) == null)
            assertFalse(map.isOccupied(new Vector2d(2,3)));
        else assertTrue (map.isOccupied(new Vector2d(2,3)));
    }

    @Test
    void objectAt() {
        assertNotNull(map.objectAt(new Vector2d(2, 2)));
        assertNotNull(map.objectAt(new Vector2d(3, 4)));
        assertNull(map.objectAt(new Vector2d(100,100)));
        assertNull(map.objectAt(new Vector2d(-100,-100)));

        if (map.isOccupied(new Vector2d(1,1)))
            assertTrue (map.objectAt(new Vector2d(1,1)) instanceof Grass);
        else assertFalse (map.objectAt(new Vector2d(1,1)) instanceof Grass);

        if (map.isOccupied(new Vector2d(2,3)))
            assertTrue (map.objectAt(new Vector2d(2,3)) instanceof Grass);
        else assertFalse (map.objectAt(new Vector2d(2,3)) instanceof Grass);

        if (map.isOccupied(new Vector2d(5,4)))
            assertTrue (map.objectAt(new Vector2d(5,4)) instanceof Grass);
        else assertFalse (map.objectAt(new Vector2d(5,4)) instanceof Grass);
    }
}
