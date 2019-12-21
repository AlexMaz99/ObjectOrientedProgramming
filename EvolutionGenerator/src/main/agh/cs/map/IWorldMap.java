package agh.cs.map;

import agh.cs.structures.Vector2d;
import agh.cs.mapElements.Animal;
import agh.cs.mapElements.IMapElement;
import com.google.common.collect.ListMultimap;

import java.util.List;
import java.util.Set;

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
     * @return True if the animal was placed.
     */
    boolean place(Animal animal);

    /**
     * Return true if given position on the map is occupied.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return objects at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Objects or empty list if the position is not occupied.
     */
    List<IMapElement> objectsAt(Vector2d position);

    /**
     * Return animals on the map.
     *
     * @return Animals or empty list if there isn't any animal on the map.
     */
    List<Animal> getAnimals();

    /**
     * Return elements on the map.
     *
     * @return IMapElement or empty list if there isn't any object on the map.
     */
    ListMultimap<Vector2d, IMapElement> getElementsMap();

    /**
     * Change the position if it is not within the boundaries of the map.
     *
     * @param position
     *            The position of the object.
     * @return correct position.
     */
    Vector2d correctPosition(Vector2d position);

    void placeFirstAnimals(int numberOfAnimals);
    List<Animal> getStrongestAnimals(List<Animal> animals);
    List<Animal> selectAnimals(List <IMapElement> elements);
    Set<Vector2d> getFreePositions();
    boolean insideJungle(Vector2d position);
    boolean areAnimalsAlive();

    Vector2d getLowerLeft();
    Vector2d getUpperRight();
    Vector2d getJungleLowerLeft();
    Vector2d getJungleUpperRight();
    int getMoveEnergy();
    int getStartEnergy();
    int getNumberOfDeadAnimals();
    int getSumOfAgeDeadAnimals();
    int getMinEnergyToReproduce();
}
