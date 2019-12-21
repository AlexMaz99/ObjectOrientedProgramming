package agh.cs.map;

import agh.cs.mapElements.Animal;
import agh.cs.mapElements.Grass;
import agh.cs.mapElements.IMapElement;
import agh.cs.structures.Vector2d;
import com.google.common.collect.ListMultimap;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class WorldMapTest {
    private IWorldMap map = new WorldMap(10, 10, 0.4, 3, 1, 20,0);

    @Test
    void createMap(){
        assertThrows(IllegalArgumentException.class, () -> {
            IWorldMap map1 = new WorldMap(0, 10, 0.4, 3, 1, 20,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            IWorldMap map2 = new WorldMap(10, 0, 0.4, 3, 1, 20,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            IWorldMap map3 = new WorldMap(10, 10, 0, 3, 1, 20,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            IWorldMap map4 = new WorldMap(10, 10, 0.4, 0, 1, 20,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            IWorldMap map5 = new WorldMap(10, 10, 0.4, 3, 0, 20,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            IWorldMap map6 = new WorldMap(10, 10, 0.4, 3, 1, 0,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            IWorldMap map7 = new WorldMap(10, 10, 1.4, 3, 1, 20,0);
        });
        assertEquals(map.getLowerLeft(), new Vector2d(0,0));
        assertEquals(map.getUpperRight(), new Vector2d(10,10));
        assertEquals(map.getMoveEnergy(), 1);
        assertEquals(map.getMinEnergyToReproduce(), 20/2);
        assertEquals(map.getStartEnergy(), 20);
    }

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
        for(Animal animal: animals){
            assertFalse(map.getFreePositions().contains(animal.getPosition()));
        }

        map.placeFirstAnimals(10);
        animals = map.getAnimals();
        assertTrue(animals.size() == 15);
        for(Animal animal: animals){
            assertFalse(map.getFreePositions().contains(animal.getPosition()));
        }
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
        assertFalse(map.getFreePositions().contains(new Vector2d(2, 3)));
        assertFalse(map.getFreePositions().contains(new Vector2d(5, 6)));
        assertFalse(map.getFreePositions().contains(new Vector2d(10, 10)));

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
                    assertTrue(map.getFreePositions().contains(new Vector2d(i,j)));
                }
            }
        }
    }

    @Test
    void freePositions(){
        Animal animal = new Animal(map, new Vector2d(1,1),10);
        map.place(animal);
        Animal animal2 = new Animal(map, new Vector2d(1,1),10);
        map.place(animal2);
        assertFalse(map.getFreePositions().contains(animal.getPosition()));
        animal.move();
        assertFalse(map.getFreePositions().contains(new Vector2d(1,1)));
        assertFalse(map.getFreePositions().contains(animal.getPosition()));


        IWorldMap map2 = new WorldMap(10,10,0.4,10,10,10, 0);

        assertTrue(map2.getFreePositions().size() == 11 * 11);
        int freePositionSize = map2.getFreePositions().size();
        for (int i = 0; i<=10; i++){
            for (int j = 0; j<=10; j++){
                map2.place(new Animal(map2, new Vector2d(i,j), 10));
                assertEquals(--freePositionSize, map2.getFreePositions().size());
                assertFalse(map2.getFreePositions().contains(new Vector2d(i,j)));
            }
        }
        assertTrue(map2.getFreePositions().isEmpty());

    }

    @Test
    void procreate() {
        Animal animal1 = new Animal(map, new Vector2d(2, 3), 20);
        Animal animal2 = new Animal(map, new Vector2d(2, 3), 20);
        Animal animal3 = new Animal(map, new Vector2d(2,3), 8);
        Animal animal4 = new Animal(map, new Vector2d(2,3),7);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);
        map.place(animal4);

        Animal animal5 = new Animal(map, new Vector2d(1, 1), 20);
        Animal animal6 = new Animal(map, new Vector2d(1, 1), 16);
        Animal animal7 = new Animal(map, new Vector2d(1,1), 8);
        Animal animal8 = new Animal(map, new Vector2d(1,1),7);
        map.place(animal5);
        map.place(animal6);
        map.place(animal7);
        map.place(animal8);

        Animal animal9 = new Animal(map, new Vector2d(3, 3), 20);
        Animal animal10 = new Animal(map, new Vector2d(3, 3), 16);
        Animal animal11 = new Animal(map, new Vector2d(3,3), 16);
        Animal animal12 = new Animal(map, new Vector2d(3,3),16);
        map.place(animal9);
        map.place(animal10);
        map.place(animal11);
        map.place(animal12);

        Animal animal13 = new Animal(map, new Vector2d(5, 5), 20);
        Animal animal14 = new Animal(map, new Vector2d(5, 5), 16);
        map.place(animal13);
        map.place(animal14);
        ((WorldMap)map).procreate();

        List<Animal> animals = map.getAnimals();
        assertEquals(map.getAnimals().size(),18);
        assertTrue(animal1.getEnergy() == 15);
        assertTrue(animal2.getEnergy() == 15);
        assertTrue(animals.get(14).getEnergy() == 10);

        assertTrue(animal5.getEnergy() == 15);
        assertTrue(animal6.getEnergy() == 12);
        assertTrue(animals.get(15).getEnergy() == 9);

        assertTrue(animal9.getEnergy() == 15);
        assertTrue(animals.get(16).getEnergy() == 9);

        assertTrue(animal13.getEnergy() == 15);
        assertTrue(animal14.getEnergy() == 12);
        assertTrue(animals.get(17).getEnergy() == 9);
        assertTrue(map.getAnimals().size()==18);

        for (Animal animal: animals){
            assertFalse(map.getFreePositions().contains(animal.getPosition()));
        }

    }

    @Test
    void eatGrass() {
        ((WorldMap)map).generateGrass();
        Vector2d position = new Vector2d(0,0);
        for (int i=0; i<=10; i++){
            for (int j=0; j<=10; j++){
                if (map.isOccupied(new Vector2d(i,j))){
                    position = new Vector2d(i,j);
                }
            }
        }
        assertFalse(map.getFreePositions().contains(position));
        Animal animal1 = new Animal(map, position, 20);
        Animal animal2 = new Animal(map, position, 20);
        Animal animal3 = new Animal(map, position, 20);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        ((WorldMap)map).eatGrass();
        assertEquals(animal1.getEnergy(), 21);
        assertEquals(animal2.getEnergy(), 21);
        assertEquals(animal3.getEnergy(), 21);

        List <IMapElement> elements = map.objectsAt(position);
        assertEquals(elements.size(), 3);
        assertFalse(map.getFreePositions().contains(position));
    }

    @RepeatedTest(value = 50, name="Test {displayName} - {currentRepetition} / {totalRepetitions}")
    void generateGrass() {
        ((WorldMap) map).generateGrass();

        int counterJungle = 0;
        int counterSavanna = 0;
        for (int i=0; i<=10; i++){
            for (int j=0; j<=10; j++){
                if (map.isOccupied(new Vector2d(i,j))){
                    if (map.insideJungle(new Vector2d(i, j))){
                        counterJungle++;
                    }
                    else {
                        counterSavanna++;
                    }
                    assertFalse(map.getFreePositions().contains(new Vector2d(i,j)));
                }
            }
        }
        assertEquals(counterJungle, 1);
        assertEquals(counterSavanna, 1);

        ((WorldMap) map).generateGrass();

        counterJungle = 0;
        counterSavanna = 0;
        for (int i=0; i<=10; i++){
            for (int j=0; j<=10; j++){
                if (map.isOccupied(new Vector2d(i,j))){
                    if (map.insideJungle(new Vector2d(i, j))){
                        counterJungle++;
                    }
                    else {
                        counterSavanna++;
                    }
                    assertFalse(map.getFreePositions().contains(new Vector2d(i,j)));
                }
            }
        }
        assertEquals(counterJungle, 2);
        assertEquals(counterSavanna, 2);

        for (int i=0; i<=10; i++){
            for (int j=0; j<=10; j++){
                map.place(new Animal(map, new Vector2d(i,j), 10));
            }
        }

        ((WorldMap) map).generateGrass();
        ((WorldMap) map).generateGrass();
        ((WorldMap) map).generateGrass();

        counterJungle = 0;
        counterSavanna = 0;
        for (int i=0; i<=10; i++){
            for (int j=0; j<=10; j++){
                if (map.isOccupied(new Vector2d(i,j)) && map.objectsAt(new Vector2d(i,j)).get(0) instanceof Grass){
                    if (map.insideJungle(new Vector2d(i, j))){
                        counterJungle++;
                    }
                    else {
                        counterSavanna++;
                    }
                }
            }
        }
        assertEquals(counterJungle, 2);
        assertEquals(counterSavanna, 2);
    }

    @Test
    void selectAnimals() {
        List <IMapElement> elements = new ArrayList<>();
        elements.add(new Grass(new Vector2d(1,1), 10));
        elements.add(new Animal(map, new Vector2d(1,1), 15));
        elements.add(new Animal(map, new Vector2d(1,1), 10));
        elements.add(new Animal(map, new Vector2d(1,1), 8));

        assertEquals(elements.size(), 4);
        List <Animal> animals = map.selectAnimals(elements);
        for (Animal animal: animals){
            assertTrue(animal instanceof Animal);
        }
        assertEquals(animals.size(), 3);

        List <IMapElement> elements2 = new ArrayList<>();
        elements2.add(new Animal(map, new Vector2d(2,2), 15));
        elements2.add(new Animal(map, new Vector2d(2,2), 10));
        elements2.add(new Animal(map, new Vector2d(2,2), 8));

        assertEquals(elements2.size(), 3);
        List <Animal> animals2 = map.selectAnimals(elements2);
        for (Animal animal: animals2){
            assertTrue(animal instanceof Animal);
        }
        assertEquals(animals2.size(), 3);

        List <IMapElement> elements3 = new ArrayList<>();
        assertEquals(elements3.size(), 0);
        List <Animal> animals3 = map.selectAnimals(elements3);
        assertEquals(animals3.size(), 0);
    }

    @Test
    void getStrongestAnimals(){
        List <Animal> animals = new ArrayList<>();
        animals.add(new Animal(map, new Vector2d(1,1),10));
        animals.add(new Animal(map, new Vector2d(1,1),9));
        animals.add(new Animal(map, new Vector2d(1,1),10));
        animals.add(new Animal(map, new Vector2d(1,1),8));
        animals.add(new Animal(map, new Vector2d(1,1),10));
        animals.add(new Animal(map, new Vector2d(1,1),5));

        List <Animal> strongestAnimals = map.getStrongestAnimals(animals);
        assertEquals(strongestAnimals.size(), 3);
        for (Animal strongestAnimal: strongestAnimals){
            assertEquals(strongestAnimal.getEnergy(), 10);
        }

        animals.add(new Animal(map, new Vector2d(1,1),20));
        animals.add(new Animal(map, new Vector2d(1,1),19));
        animals.add(new Animal(map, new Vector2d(1,1),100));

        List <Animal> strongestAnimals2 = map.getStrongestAnimals(animals);
        assertEquals(strongestAnimals2.size(), 1);
        assertEquals(strongestAnimals2.get(0).getEnergy(), 100);
    }

    @Test
    void insideJungle(){
        Vector2d jungleLowerLeft = map.getJungleLowerLeft();
        Vector2d jungleUpperRight = map.getJungleUpperRight();
        Vector2d position1 = map.getLowerLeft();
        Vector2d position2 = new Vector2d(jungleLowerLeft.x, jungleLowerLeft.y);
        Vector2d position3 = new Vector2d(jungleUpperRight.x, jungleUpperRight.y);
        Vector2d position4 = new Vector2d(jungleLowerLeft.x, jungleUpperRight.y);
        Vector2d position5 = new Vector2d(jungleUpperRight.x, jungleLowerLeft.y);
        Vector2d position6 = map.getUpperRight();

        assertFalse(map.insideJungle(position1));
        assertTrue(map.insideJungle(position2));
        assertTrue(map.insideJungle(position3));
        assertTrue(map.insideJungle(position4));
        assertTrue(map.insideJungle(position5));
        assertFalse(map.insideJungle(position6));
    }

    @Test
    void removeDeadAnimals(){
        Animal animal1  = new Animal(map, new Vector2d(1,1), 1);
        Animal animal2 = new Animal(map, new Vector2d(4,4), 2);

        map.place(animal1);
        map.place(animal2);
        assertEquals(map.getAnimals().size(), 2);

        ((WorldMap)map).removeDeadAnimals();
        assertEquals(map.getAnimals().size(), 2);
        assertEquals(map.getNumberOfDeadAnimals(), 0);

        animal1.move();
        animal2.move();
        ((WorldMap)map).removeDeadAnimals();
        assertEquals(map.getAnimals().size(), 1);
        assertEquals(map.getNumberOfDeadAnimals(), 1);

        animal2.move();
        ((WorldMap)map).removeDeadAnimals();
        assertEquals(map.getAnimals().size(), 0);

        assertFalse(map.areAnimalsAlive());
        assertEquals(map.getNumberOfDeadAnimals(), 2);
    }
}
