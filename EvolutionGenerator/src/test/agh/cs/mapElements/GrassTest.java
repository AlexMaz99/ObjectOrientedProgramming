package agh.cs.mapElements;

import agh.cs.mapElements.Grass;
import agh.cs.position.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassTest {
    private Grass grass1 = new Grass(new Vector2d(1,2),5);
    private Grass grass2 = new Grass(new Vector2d (5,10),4);

    @Test
    void GrassToString(){
        assertEquals(grass1.toString(), "*");
        assertEquals(grass2.toString(), "*");
    }

    @Test
    void getPosition(){
        assertEquals(grass1.getPosition(), new Vector2d (1,2));
        assertEquals(grass2.getPosition(), new Vector2d (5,10));
    }
}
