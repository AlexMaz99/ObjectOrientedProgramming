package agh.cs.mapElements;

import agh.cs.map.IWorldMap;
import agh.cs.map.WorldMap;
import agh.cs.structures.Vector2d;
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
        assertEquals(map.getAnimals().size(), 3);
        Animal baby = map.getAnimals().get(2);
        assertEquals(baby.getEnergy(), (15/4 + 20/4));
        assertEquals(animal1.getEnergy(), 15 - 15/4);
        assertEquals(animal2.getEnergy(), 20 - 20/4);

        Animal animal3 = new Animal(map, new Vector2d(3,4), 2);
        Animal animal4 = new Animal(map, new Vector2d(3,4), 2);
        map.place(animal3);
        map.place(animal4);
        animal3.reproduce(animal4);
        assertNotEquals(map.getAnimals().size(), 6);
        assertEquals(map.getAnimals().size(), 5);
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

   @RepeatedTest(value = 20, name="Test {displayName} - {currentRepetition} / {totalRepetitions}")
    void babyPosition(){
        Animal parent = new Animal(map, new Vector2d(3,3), 15);
        Animal parent2 = new Animal(map, new Vector2d(3,3), 15);
        Animal animal2 = new Animal(map, new Vector2d(2,2), 20);
        map.place(parent);
        map.place(parent2);
        map.place(animal2);

        Vector2d vector23 = new Vector2d(2,3);
        Vector2d vector24 = new Vector2d(2,4);
        Vector2d vector34 = new Vector2d(3,4);
        Vector2d vector44 = new Vector2d(4,4);
        Vector2d vector43 = new Vector2d(4,3);
        Vector2d vector42 = new Vector2d(4,2);
        Vector2d vector32 = new Vector2d(3,2);


        Vector2d babyPosition = parent.babyPosition(parent);
        assertTrue(babyPosition.equals(vector23)
                    || babyPosition.equals(vector24)
                    || babyPosition.equals(vector34)
                    || babyPosition.equals(vector44)
                    || babyPosition.equals(vector43)
                    || babyPosition.equals(vector42)
                    || babyPosition.equals(vector32));

        map.place(new Animal(map, vector23, 10));
        babyPosition = parent.babyPosition(parent);
        assertTrue(babyPosition.equals(vector24)
               || babyPosition.equals(vector34)
               || babyPosition.equals(vector44)
               || babyPosition.equals(vector43)
               || babyPosition.equals(vector42)
               || babyPosition.equals(vector32));

       map.place(new Animal(map, vector24, 10));
       babyPosition = parent.babyPosition(parent);
       assertTrue(babyPosition.equals(vector34)
               || babyPosition.equals(vector44)
               || babyPosition.equals(vector43)
               || babyPosition.equals(vector42)
               || babyPosition.equals(vector32));

       map.place(new Animal(map, vector34, 10));
       babyPosition = parent.babyPosition(parent);
       assertTrue(babyPosition.equals(vector44)
               || babyPosition.equals(vector43)
               || babyPosition.equals(vector42)
               || babyPosition.equals(vector32));

       map.place(new Animal(map, vector44, 10));
       babyPosition = parent.babyPosition(parent);
       assertTrue(babyPosition.equals(vector43)
               || babyPosition.equals(vector42)
               || babyPosition.equals(vector32));

       map.place(new Animal(map, vector43, 10));
       babyPosition = parent.babyPosition(parent);
       assertTrue(babyPosition.equals(vector42)
               || babyPosition.equals(vector32));

       map.place(new Animal(map, vector42, 10));
       babyPosition = parent.babyPosition(parent);
       assertEquals(babyPosition, vector32);

       map.place(new Animal(map, vector32, 10));
       babyPosition = parent.babyPosition(parent);
       assertEquals(babyPosition, new Vector2d(3,3));
    }
}
