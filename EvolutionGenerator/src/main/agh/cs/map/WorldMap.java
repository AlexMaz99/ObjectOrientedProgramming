package agh.cs.map;

import agh.cs.mapElements.Grass;
import agh.cs.position.Vector2d;
import agh.cs.mapElements.Animal;
import agh.cs.mapElements.IMapElement;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.*;

public class WorldMap implements IWorldMap, IPositionChangeObserver { //TODO: improve methods

    protected List<Animal> animals = new ArrayList<>();
    protected ListMultimap<Vector2d, IMapElement> elementsMap = ArrayListMultimap.create();

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
        }
        eatGrass();
        procreate();
        generateGrass();
        System.out.println("Number of animals: " + animals.size());
        System.out.println(this);
    }
    private void removeDeadAnimals(){
        List <Animal> deadAnimals =  new ArrayList<>();
        for (Animal animal : this.animals){
            if (animal.isDead()){
                deadAnimals.add(animal);
            }
        }
        for (Animal deadAnimal : deadAnimals){
            this.removeAnimal(deadAnimal);
        }
    }
    private void removeAnimal (Animal animal){
        List <IMapElement> elements =  this.elementsMap.get(animal.getPosition());
        this.animals.remove(animal);
        elements.remove(animal);
    }
    private void removeGrass(Grass grass){
        List <IMapElement> elements =  this.elementsMap.get(grass.getPosition());
        elements.remove(grass);
    }
    private void eatGrass (){
        for (Animal animal: this.animals){
            List <IMapElement> elements = this.objectsAt(animal.getPosition());
            if(elements.get(0) instanceof Grass){
                if(animal.eatGrass((Grass)elements.get(0))){
                    removeGrass((Grass)elements.get(0));
                }
            }
        }
    }
    private void procreate (){
        List <Vector2d> positions = new ArrayList<>();
        for (Animal animal: this.animals){
            if (!positions.contains(animal.getPosition())){
                positions.add(animal.getPosition());
            }
        }
        for (Vector2d position: positions){
            List <IMapElement> elements = this.objectsAt(position);
            List <Animal> animalsToProcreate = new ArrayList<>();
            for (IMapElement element : elements)
                if (element instanceof Animal)
                    animalsToProcreate.add((Animal)element);
            if (animalsToProcreate.size()>1){
                Animal strongestAnimal=animalsToProcreate.get(0);
                for (Animal animal: animalsToProcreate){
                    if (animal.getEnergy() > strongestAnimal.getEnergy())
                        strongestAnimal = animal;
                }
                animalsToProcreate.remove(strongestAnimal);
                Animal secondStrongestAnimal = animalsToProcreate.get(0);
                for (Animal animal: animalsToProcreate){
                    if (animal.getEnergy() > secondStrongestAnimal.getEnergy())
                        secondStrongestAnimal = animal;
                }
                strongestAnimal.reproduce(secondStrongestAnimal);
            }
        }
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
        /*return this.elementsMap.containsKey(position);*/
        return objectsAt(position).size()!=0;
    }

    @Override
    public List <IMapElement> objectsAt(Vector2d position) {
        return elementsMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        List <IMapElement> animals =  this.elementsMap.get(oldPosition);
        Animal animalToDelete = null;
        boolean delete = false;
        for (IMapElement animal : animals){
            if (animal.getPosition().equals(newPosition)){
                animalToDelete = (Animal) animal;
                delete = true;
                break;
            }
        }
        if (delete) {
            animals.remove(animalToDelete);
            elementsMap.put(newPosition, animalToDelete);
        }
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
    public List<Animal> getAnimals(){
        return this.animals;
    }
    public Vector2d getLowerLeft(){ return this.lowerLeft; }
    public Vector2d getUpperRight() { return this.upperRight; }
    public Vector2d getJungleLowerLeft() { return this.jungleLowerLeft; }
    public Vector2d getJungleUpperRight() { return this.jungleUpperRight; }
    public ListMultimap<Vector2d, IMapElement> getElementsMap() { return this.elementsMap; }
}
