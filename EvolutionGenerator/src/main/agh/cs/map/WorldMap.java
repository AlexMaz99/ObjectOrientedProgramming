package agh.cs.map;

import agh.cs.mapElements.Grass;
import agh.cs.position.Vector2d;
import agh.cs.mapElements.Animal;
import agh.cs.mapElements.IMapElement;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

public class WorldMap implements IWorldMap, IPositionChangeObserver {

    protected List<Animal> animals = new ArrayList<>();
    protected Map<Vector2d, IMapElement> elementsMap = new HashMap<>();
    protected Multimap<Vector2d, Animal> animalsMap = LinkedListMultimap.create();

    private MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private final int plantEnergy;
    private final int moveEnergy;
    private final int startEnergy;

    public WorldMap(int width, int height, double jungleRatio, int plantEnergy, int moveEnergy, int startEnergy){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d (width, height);

        int jungleWidth = (int) ((double) width * jungleRatio);
        int jungleHeight = (int) ((double) height * jungleRatio);
        this.jungleLowerLeft = new Vector2d ((width - jungleWidth)/2, (height - jungleHeight)/2);
        this.jungleUpperRight = new Vector2d (this.jungleLowerLeft.x + jungleWidth, this.jungleLowerLeft.y + jungleHeight);

        this.plantEnergy = plantEnergy;
        this.moveEnergy = moveEnergy;
        this.startEnergy = startEnergy;

        if (!lowerLeft.precedes(jungleLowerLeft)) {
            throw new IllegalArgumentException("Jungle lower left corner can't precede map lower left corner"+this.jungleLowerLeft);
        }
        if (!upperRight.follows(jungleUpperRight)) {
            throw new IllegalArgumentException("Jungle upper right corner can't follow map upper right corner");
        }
    }

    public void anotherDay (){
        removeDeadAnimals();
        for (Animal animal : animals){
            animal.move();
            System.out.println(animal.getEnergy());
            System.out.println(animal.getPosition());
        }
        // eat grass

        generateGrass();
        System.out.println(this);
    }
    private void removeDeadAnimals(){
        for (Animal animal : animals)
            if (animal.isDead())
                this.removeAnimal(animal);
    }
    private void removeAnimal (Animal animal){
        this.animals.remove(animal);
        this.elementsMap.remove(animal);
    }
    public void removeGrass(Grass grass){
        elementsMap.remove(grass.getPosition());
    }

    public Vector2d correctPosition(Vector2d position){
        int x, y;
        if (position.x > upperRight.x) x = 0;
        else if (position.x < 0) x = upperRight.x;
        else x = position.x;

        if (position.y > upperRight.y) y = 0;
        else if (position.y < 0) y = upperRight.y;
        else y = position.y;
        return new Vector2d (x,y);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public boolean place(Animal animal) {
        if (!(lowerLeft.precedes(animal.getPosition()) && upperRight.follows(animal.getPosition()))) {
            throw new IllegalArgumentException(animal.getPosition().toString() + " is out of map bounds");
        }
        this.animals.add (animal);
        this.elementsMap.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }

    public void placeFirstAnimals (int numberOfAnimals){
        for (int i=0; i<numberOfAnimals; i++){
            int x, y;
            Vector2d animalPosition;
            do{
                x = new Random().nextInt(this.upperRight.x + 1);
                y = new Random().nextInt(this.upperRight.y + 1);
                animalPosition = new Vector2d(x,y);
            } while (this.isOccupied(animalPosition));
            Animal animal = new Animal(this, animalPosition, this.startEnergy);

            animal.addObserver(this);
            this.animals.add(animal);
            this.elementsMap.put(animalPosition, animal);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.elementsMap.containsKey(position);
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        return elementsMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = (Animal) this.elementsMap.get(oldPosition);
        this.elementsMap.remove(oldPosition);
        this.elementsMap.put(newPosition, animal);
    }
    @Override
    public String toString(){
        return mapVisualizer.draw(this.lowerLeft, this.upperRight);
    }

    private void generateGrass(){
        int x,y;
        int counter = (upperRight.x - lowerLeft.x) * (upperRight.y - lowerLeft.y);
        Vector2d grassPosition;
        do{
            x = new Random().nextInt(upperRight.x + 1);
            y = new Random().nextInt(upperRight.y + 1);
            grassPosition = new Vector2d(x,y);
        } while (this.isOccupied(grassPosition) && jungleLowerLeft.precedes(grassPosition) && jungleUpperRight.follows(grassPosition) && counter -- >=0);
        this.elementsMap.put (grassPosition, new Grass(grassPosition, plantEnergy));

        counter = (jungleUpperRight.x - jungleLowerLeft.x) * (jungleUpperRight.y - jungleLowerLeft.y);
        do{
            x = new Random().nextInt(jungleUpperRight.x - jungleLowerLeft.x + 1) + jungleLowerLeft.x;
            y = new Random().nextInt (jungleUpperRight.y - jungleLowerLeft.y + 1) + jungleLowerLeft.y;
            grassPosition = new Vector2d(x,y);
        } while (this.isOccupied(grassPosition) && counter>=0);
        this.elementsMap.put (grassPosition, new Grass(grassPosition, plantEnergy));
    }
    public int getMoveEnergy(){
        return this.moveEnergy;
    }
}
