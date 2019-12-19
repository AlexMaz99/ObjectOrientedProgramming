package agh.cs.mapElements;

import agh.cs.map.IWorldMap;
import agh.cs.map.WorldMap;
import agh.cs.position.Vector2d;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    private IWorldMap map = new WorldMap(10,20,0.4,6,1,15, 0);

    @RepeatedTest(value = 100, name="Test {displayName} - {currentRepetition} / {totalRepetitions}")
    void move(){
        map.placeFirstAnimals(10);
        Vector2d position;
        int startEnergy = 15;
        for (Animal animal : map.getAnimals()){
            position = animal.getPosition();
            animal.move();
            position = map.correctPosition(position.add(animal.getDirection().toUnitVector()));
            assertEquals(animal.getPosition(), position);
            assertEquals(animal.getEnergy(), startEnergy - 1);
            assertTrue(map.getAnimals().size() == 10);
        }
        for (Animal animal : map.getAnimals()){
            position = animal.getPosition();
            animal.move();
            position = map.correctPosition(position.add(animal.getDirection().toUnitVector()));
            assertEquals(animal.getPosition(), position);
            assertEquals(animal.getEnergy(), startEnergy - 2);
            assertTrue(map.getAnimals().size() == 10);
        }
    }

    @Test
    void eatGrass(){
        map.placeFirstAnimals(2);
        Animal animal1 = map.getAnimals().get(0);
        Animal animal2 = map.getAnimals().get(1);
        Grass grass1 = new Grass (animal1.getPosition(), 6);
        Grass grass2 = new Grass (animal2.getPosition(), 6);

        int startEnergy1 = animal1.getEnergy();
        int startEnergy2 = animal2.getEnergy();
        animal1.eatGrass(grass1);
        animal2.eatGrass(grass2);

        assertEquals(animal1.getEnergy(), startEnergy1 + grass1.getProtein());
        assertEquals(animal2.getEnergy(), startEnergy2 + grass2.getProtein());

        Animal animal3 = new Animal(map, animal1.getPosition(), 21);
        Animal animal4 = new Animal(map, animal1.getPosition(), 21);
        map.place(animal3);
        map.place(animal4);
        animal1.eatGrass(grass1);
        assertEquals(animal1.getEnergy(), 21 + grass1.getProtein()/3);
        assertEquals(animal3.getEnergy(), 21 + grass1.getProtein()/3);
        assertEquals(animal4.getEnergy(), 21 + grass1.getProtein()/3);

        Animal animal5 = new Animal(map, animal1.getPosition(), 15);
        map.place(animal5);
        animal1.eatGrass(grass1);
        assertEquals(animal1.getEnergy(), 23 + grass1.getProtein()/3);
        assertEquals(animal3.getEnergy(), 23 + grass1.getProtein()/3);
        assertEquals(animal4.getEnergy(), 23 + grass1.getProtein()/3);
        assertEquals(animal5.getEnergy(), 15);
    }

    @Test
    void canReproduceAndIsDead(){
        Animal animal1 = new Animal(map, new Vector2d(1,1), 15);
        for (int i=0; i<15; i++){
            if (animal1.getEnergy() >= 7) assertTrue(animal1.canReproduce());
            else assertFalse(animal1.canReproduce());
            animal1.move();
        }
        assertTrue(animal1.isDead());
    }

    @Test
    void reproduce() {
        Animal animal1 = new Animal(map, new Vector2d(1,2), 15);
        Animal animal2 = new Animal(map, new Vector2d(1,2), 20);
        map.place(animal1);
        map.place(animal2);

        animal1.reproduce(animal2);
        assertTrue(map.getAnimals().size() == 3);
        Animal baby = map.getAnimals().get(2);
        assertEquals(baby.getEnergy(), (15/4 + 20/4));
        assertEquals(animal1.getEnergy(), 15 - 15/4);
        assertEquals(animal2.getEnergy(), 20 - 20/4);

        Animal animal3 = new Animal(map, new Vector2d(3,4), 2);
        Animal animal4 = new Animal(map, new Vector2d(3,4), 2);
        map.place(animal3);
        map.place(animal4);
        animal3.reproduce(animal4);
        assertFalse(map.getAnimals().size() == 6);
        assertTrue(map.getAnimals().size() == 5);
    }

    @Test
    void animalToString(){
        Animal animal1 = new Animal(map, new Vector2d(1,1), 20);
        Animal animal2 = new Animal(map, new Vector2d(1,1), 15);
        map.place(animal1);
        map.place(animal2);
        for (int i=0; i<19; i++){
            animal1.move();
            assertEquals(animal1.toString(), "\uD83D\uDC3B");
        }
        animal1.move();
        assertEquals(animal1.toString(),"❌" );

        for (int i=0; i<14; i++){
            animal2.move();
            assertEquals(animal2.toString(),"\uD83D\uDC3B" );
        }
        animal2.move();
        assertEquals(animal2.toString(),"❌" );
    }
}
