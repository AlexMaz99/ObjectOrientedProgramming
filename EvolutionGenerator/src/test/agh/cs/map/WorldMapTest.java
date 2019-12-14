package agh.cs.map;

import agh.cs.mapElements.Animal;
import agh.cs.mapElements.IMapElement;
import agh.cs.position.Vector2d;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorldMapTest { //TODO: make tests
    private IWorldMap map = new WorldMap(10, 10, 0.4, 3, 1, 20);
    @Test
    void correctPosition() {
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();

        Vector2d position = new Vector2d(upperRight.x + 1, upperRight.y + 1);
        position = map.correctPosition(position);
        assertEquals(position, new Vector2d(lowerLeft.x,lowerLeft.y));

        position = new Vector2d(upperRight.x + 1, 5);
        position = map.correctPosition(position);
        assertEquals(position, new Vector2d(lowerLeft.x, 5));

        position = new Vector2d(2, upperRight.x + 1);
        position = map.correctPosition(position);
        assertEquals(position, new Vector2d(2, lowerLeft.y));

        position = new Vector2d(1, 1);
        position = map.correctPosition(position);
        assertEquals(position, new Vector2d(1, 1));

        position = new Vector2d(lowerLeft.x - 1, 5);
        position = map.correctPosition(position);
        assertEquals(position, new Vector2d(upperRight.x, 5));

        position = new Vector2d(2, lowerLeft.y - 1);
        position = map.correctPosition(position);
        assertEquals(position, new Vector2d(2, upperRight.x));

        position = new Vector2d(lowerLeft.x - 1, lowerLeft.y - 1);
        position = map.correctPosition(position);
        assertEquals(position, new Vector2d(upperRight.x, upperRight.y));
    }

    @Test
    void place() {
        Animal animal1 = new Animal(map, new Vector2d(10*2, 10*2), 20);
        Animal animal2 = new Animal(map, new Vector2d(-5, -5), 20);
        Animal animal3 = new Animal(map, new Vector2d(6,20), 20);
        Animal animal4 = new Animal(map, new Vector2d(-1,5),20);
        assertThrows(IllegalArgumentException.class, () -> {
            map.place(animal1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            map.place(animal2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            map.place(animal3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            map.place(animal4);
        });
        Animal animal5 = new Animal(map, new Vector2d(2, 3), 20);
        Animal animal6 = new Animal(map, new Vector2d(5, 6), 20);
        Animal animal7 = new Animal(map, new Vector2d(10,10), 20);
        Animal animal8 = new Animal(map, new Vector2d(2,3),20);
        map.place(animal5);
        assertTrue(map.getAnimals().contains(animal5));
        map.place(animal6);
        assertTrue(map.getAnimals().contains(animal6));
        map.place(animal7);
        assertTrue(map.getAnimals().contains(animal7));
        map.place(animal8);
        assertTrue(map.getAnimals().contains(animal8));

        ListMultimap<Vector2d, IMapElement> elementsMap = map.getElementsMap();

        List<IMapElement> elements = elementsMap.get(new Vector2d(2,3));
        assertTrue(elements.contains(animal5));
        assertTrue(elements.contains(animal8));

        elements = elementsMap.get(new Vector2d(5,6));
        assertTrue(elements.contains(animal6));

        elements = elementsMap.get(new Vector2d(10,10));
        assertTrue(elements.contains(animal7));

        elements = elementsMap.get(new Vector2d(1,1));
        assertTrue(elements.isEmpty());

        elements = elementsMap.get(new Vector2d(5,5));
        assertTrue(elements.isEmpty());
    }

    @Test
    void placeFirstAnimals() {
        map.placeFirstAnimals(5);
        List <Animal> animals = map.getAnimals();
        assertTrue(animals.size() == 5);

        map.placeFirstAnimals(10);
        animals = map.getAnimals();
        assertTrue(animals.size() == 15);
    }

    @Test
    void isOccupied() {
        Animal animal1 = new Animal(map, new Vector2d(2, 3), 20);
        Animal animal2 = new Animal(map, new Vector2d(5, 6), 20);
        Animal animal3 = new Animal(map, new Vector2d(10,10), 20);
        Animal animal4 = new Animal(map, new Vector2d(2,3),20);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);
        map.place(animal4);

        for (int i = 0; i <= 10; i++){
            for (int j=0; j<= 10; j++){
                if (!((i==2 && j==3) || (i==5 && j==6) || (i==10 && j==10))){
                    assertFalse(map.isOccupied(new Vector2d(i,j)));
                }
                else {
                    assertTrue(map.isOccupied(new Vector2d(i,j)));
                }
            }
        }
    }

    @Test
    void objectsAt() {
        Animal animal1 = new Animal(map, new Vector2d(2, 3), 20);
        Animal animal2 = new Animal(map, new Vector2d(5, 6), 20);
        Animal animal3 = new Animal(map, new Vector2d(10,10), 20);
        Animal animal4 = new Animal(map, new Vector2d(2,3),20);
        Animal animal5 = new Animal(map, new Vector2d(2,3), 20);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);
        map.place(animal4);
        map.place(animal5);

        List <IMapElement> elements = map.objectsAt(new Vector2d(2,3));
        assertTrue(elements.contains(animal1));
        assertTrue(elements.contains(animal4));
        assertTrue(elements.contains(animal5));
        assertEquals(elements.size(), 3);

        elements = map.objectsAt(new Vector2d(5,6));
        assertTrue(elements.contains(animal2));
        assertEquals(elements.size(), 1);

        elements = map.objectsAt(new Vector2d(10,10));
        assertTrue(elements.contains(animal3));
        assertEquals(elements.size(), 1);

        for (int i=0; i<=10; i++){
            for (int j=0; j<=10; j++){
                if (!((i==2 && j==3) || (i==5 && j==6) || (i==10 && j==10))){
                    elements = map.objectsAt(new Vector2d(i,j));
                    assertTrue(elements.isEmpty());
                }
            }
        }
    }

}
