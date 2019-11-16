import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab3.OptionsParsers;
import agh.cs.lab4.RectangularMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    private RectangularMap map;
    private List<Animal> animals = new ArrayList<>();
    private int width = 10;
    private int height = 5;

    @BeforeEach
    void setUp(){
        map = new RectangularMap(width, height);
        map.place(new Animal (map));
        map.place(new Animal (map, new Vector2d(3,4)));
    }
    @Test
    void canMoveTo(){
        assertTrue (map.canMoveTo(new Vector2d(5,5)));
        assertTrue (map.canMoveTo(new Vector2d(0,1)));
        assertTrue (map.canMoveTo(new Vector2d(9,4)));
        assertFalse (map.canMoveTo(new Vector2d(2,2)));
        assertFalse (map.canMoveTo(new Vector2d(6,6)));
        assertFalse (map.canMoveTo(new Vector2d(3,4)));
    }

    @Test
    void place(){
        assertTrue (map.place(new Animal (map, new Vector2d(1,1))));
        assertTrue (map.place(new Animal (map, new Vector2d(6,4))));
        assertThrows(IllegalArgumentException.class, () -> {
            map.place(new Animal(map, new Vector2d(2, 2)));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            map.place(new Animal(map, new Vector2d(3, 4)));
        });
    }

    @Test
    void run(){
        MoveDirection [] directions = new OptionsParsers().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        map.run(directions);

        assertNotNull(map.objectAt(new Vector2d(3,5)));
        assertNotNull(map.objectAt(new Vector2d(2,0)));
        assertNull(map.objectAt(new Vector2d(7,3)));
        assertNull(map.objectAt(new Vector2d(0,0)));

        animals = map.getAnimals();

        assertEquals(animals.get(0).getPosition(), new Vector2d(2, 0));
        assertEquals(animals.get(1).getPosition(), new Vector2d(3, 5));
        assertEquals(animals.get(0).toString(), "v");
        assertEquals(animals.get(1).toString(), "^");
    }
    @Test
    void isOccupied(){
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j <= width; j++) {
                if ((i == 2 && j == 2) || (i == 3 && j == 4))
                    assertTrue(map.isOccupied(new Vector2d(i, j)));
                else assertFalse(map.isOccupied(new Vector2d(i, j)));
            }
        }
    }

    @Test
    void ObjectAt(){
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j <=width; j++) {
                if ((i == 2 && j == 2) || (i == 3 && j == 4))
                    assertNotNull(map.objectAt(new Vector2d(i,j)));
                else assertNull(map.objectAt(new Vector2d(i,j)));
            }
        }
    }
}
