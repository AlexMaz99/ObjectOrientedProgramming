package agh.cs.map;

import agh.cs.mapElements.Grass;
import agh.cs.position.Vector2d;
import agh.cs.mapElements.Animal;
import agh.cs.mapElements.IMapElement;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.*;

import static java.lang.Math.max;

public class WorldMap implements IWorldMap, IPositionChangeObserver {

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
    private int day = 0;
    private Random rand = new Random();

    public WorldMap(int width, int height, double jungleRatio, int plantEnergy, int moveEnergy, int startEnergy, int startNumberOfAnimals){
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
            throw new IllegalArgumentException("Jungle lower left corner can't precede map lower left corner");
        }
        if (!upperRight.follows(jungleUpperRight)) {
            throw new IllegalArgumentException("Jungle upper right corner can't follow map upper right corner");
        }

        this.placeFirstAnimals(startNumberOfAnimals);
    }

    public void anotherDay (){
        removeDeadAnimals();
        for (Animal animal : animals){
            animal.move();
        }
        eatGrass();
        procreate();
        generateGrass();
        this.day++;
        if (this.day % 100 == 0) {
            System.out.println("Day " + this.day);
            System.out.println("Number of animals: " + this.animals.size());
            System.out.println(this);
        }
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
        /*List <IMapElement> elements =  this.elementsMap.get(animal.getPosition());
        elements.remove(animal);*/
        this.animals.remove(animal);
        this.elementsMap.remove(animal.getPosition(), animal);
    }
    private void removeGrass(Grass grass){
        /*List <IMapElement> elements =  this.elementsMap.get(grass.getPosition());
        elements.remove(grass);*/
        this.elementsMap.remove(grass.getPosition(), grass);
    }
    public void eatGrass (){
        for (Animal animal: this.animals){
            List <IMapElement> elements = this.objectsAt(animal.getPosition());
            if(elements.get(0) instanceof Grass && elements.size()>1){
                animal.eatGrass((Grass)elements.get(0));
                removeGrass((Grass)elements.get(0));
            }
        }
    }
    public void procreate () {
        List<Vector2d> positions = new ArrayList<>(); //lists of positions with at least 2 animals
        for (Animal animal : this.animals) {
            if (objectsAt(animal.getPosition()).size() > 1 && !positions.contains(animal.getPosition())) {
                positions.add(animal.getPosition());
            }
        }

        for (Vector2d position : positions) {
            List<Animal> animalsAtTheSamePosition = this.chooseAnimals(this.objectsAt(position));
            if (animalsAtTheSamePosition.size() > 1) {
                List<Animal> strongestAnimals = this.getStrongestAnimals(animalsAtTheSamePosition); // list of animals with highest energy on the position
                if (strongestAnimals.size() == 2) strongestAnimals.get(0).reproduce(strongestAnimals.get(1)); // if there are two strongest animals with the same energy

                else if (strongestAnimals.size() > 2) { // if there are more than two strongest animals, we randomly select two of them
                    int x = new Random().nextInt(strongestAnimals.size());
                    int y;
                    do {
                        y = new Random().nextInt(strongestAnimals.size());
                    } while (x == y);
                    strongestAnimals.get(x).reproduce(strongestAnimals.get(y));
                }

                else if (strongestAnimals.size() == 1) { // if there is only one strongest animal
                    Animal animal1 = strongestAnimals.get(0);
                    List<Animal> secondStrongestAnimals = animalsAtTheSamePosition; // list of animals with the second highest energy on the position

                    secondStrongestAnimals.remove(animal1);
                    secondStrongestAnimals = this.getStrongestAnimals(secondStrongestAnimals);

                    if (secondStrongestAnimals.size() == 1) animal1.reproduce(secondStrongestAnimals.get(0));

                    else{
                        int x = new Random().nextInt(secondStrongestAnimals.size());
                        animal1.reproduce(secondStrongestAnimals.get(x));
                    }
                }
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

    public void generateGrass(){
        int xSavanna,ySavanna;
        int savannaArea = (upperRight.x - lowerLeft.x) * (upperRight.y - lowerLeft.y);
        Vector2d savannaGrass;
        do{
            xSavanna = rand.nextInt(upperRight.x + 1);
            ySavanna = rand.nextInt(upperRight.y + 1);
            savannaGrass = new Vector2d(xSavanna,ySavanna);
            savannaArea --;
            if (savannaArea < 0) break;
        } while (this.isOccupied(savannaGrass) || this.insideJungle(savannaGrass));
        this.elementsMap.put (savannaGrass, new Grass(savannaGrass, plantEnergy));

        int jungleArea = (jungleUpperRight.x - jungleLowerLeft.x) * (jungleUpperRight.y - jungleLowerLeft.y);
        int xJungle, yJungle;
        Vector2d jungleGrass;

        do{
            xJungle = rand.nextInt(jungleUpperRight.x - jungleLowerLeft.x + 1) + jungleLowerLeft.x;
            yJungle = rand.nextInt (jungleUpperRight.y - jungleLowerLeft.y + 1) + jungleLowerLeft.y;
            jungleGrass = new Vector2d(xJungle,yJungle);
            jungleArea--;
            if (jungleArea < 0) break;
        } while (this.isOccupied(jungleGrass));
        this.elementsMap.put (jungleGrass, new Grass(jungleGrass, plantEnergy));
    }
    public List<Animal> chooseAnimals(List <IMapElement> elements){
        List <Animal> animals = new ArrayList<>();
        for (IMapElement element: elements){
            if (element instanceof Animal){
                animals.add((Animal)element);
            }
        }
        return animals;
    }
    public List<Animal> getStrongestAnimals(List<Animal> animals){
        int maxEnergy = -1;
        for (Animal animal: animals){
            maxEnergy = max (maxEnergy, animal.getEnergy());
        }
        List <Animal> strongestAnimals = new ArrayList<>();

        for (Animal animal: animals){
            if (animal.getEnergy() == maxEnergy){
                strongestAnimals.add(animal);
            }
        }
        return strongestAnimals;
    }
    public int getMoveEnergy(){
        return this.moveEnergy;
    }

    public boolean insideJungle (Vector2d position){
        return position.follows(this.jungleLowerLeft) && position.precedes(this.jungleUpperRight);
    }
    public List<Animal> getAnimals(){
        return this.animals;
    }
    public Vector2d getLowerLeft(){ return this.lowerLeft; }
    public Vector2d getUpperRight() { return this.upperRight; }
    public Vector2d getJungleLowerLeft() { return this.jungleLowerLeft; }
    public Vector2d getJungleUpperRight() { return this.jungleUpperRight; }
    public int getDay() { return this.day; }
    public ListMultimap<Vector2d, IMapElement> getElementsMap() { return this.elementsMap; }
}
