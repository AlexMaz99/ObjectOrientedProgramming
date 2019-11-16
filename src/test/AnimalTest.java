import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab4.RectangularMap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static agh.cs.lab2.MoveDirection.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    private List<Animal> animals = new ArrayList<>();
    @Test
    void move(){
        RectangularMap map = new RectangularMap (4,4);
        map.place(new Animal (map));
        animals=map.getAnimals();
        Animal dog=animals.get(0);
        dog.move(RIGHT);
        assertEquals( dog.toString(), ">" );
        assertEquals(dog.getPosition(), new Vector2d(2,2));
        dog.move(LEFT);
        assertEquals( dog.toString(), "^" );
        assertEquals(dog.getPosition(), new Vector2d(2,2));
        dog.move(FORWARD);
        assertEquals( dog.toString(), "^" );
        assertEquals(dog.getPosition(), new Vector2d(2,3));
        dog.move(BACKWARD);
        assertEquals( dog.toString(), "^" );
        assertEquals(dog.getPosition(), new Vector2d(2,2));
        for (int i=0; i<5; i++)
            dog.move(FORWARD);
        assertEquals(dog.toString(), "^" );
        assertEquals(dog.getPosition(), new Vector2d(2,4));
        for (int i=0; i<8; i++)
            dog.move(BACKWARD);
        assertEquals(dog.toString(), "^" );
        assertEquals(dog.getPosition(), new Vector2d(2,0));
    }
}
