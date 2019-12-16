package agh.cs.map;

import agh.cs.position.Vector2d;
import agh.cs.mapElements.Animal;
import agh.cs.mapElements.IMapElement;
import com.google.common.collect.ListMultimap;

import java.util.List;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    boolean place(Animal animal);
/*
    /**
     * Move the animal on the map according to the provided move directions. Every
     * n-th direction should be sent to the n-th animal on the map.
     *
     * @param directions
     *            Array of move directions.
     */

    /*void run(MapDirection[] directions);*/

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    List<IMapElement> objectsAt(Vector2d position);
    List<Animal> getAnimals();
    ListMultimap<Vector2d, IMapElement> getElementsMap();
    Vector2d correctPosition(Vector2d position);
    Vector2d getLowerLeft();
    Vector2d getUpperRight();
    Vector2d getJungleLowerLeft();
    Vector2d getJungleUpperRight();
    int getMoveEnergy();
    int getDay();

    void placeFirstAnimals(int numberOfAnimals);
    List<Animal> getStrongestAnimals(List<Animal> animals);
    List<Animal> chooseAnimals(List <IMapElement> elements);
    void procreate();
    void generateGrass();
    void eatGrass();
    void anotherDay();
    boolean insideJungle(Vector2d position);
}
