package agh.cs.mapElements;

import agh.cs.map.IWorldMap;
import agh.cs.map.IPositionChangeObserver;
import agh.cs.position.MapDirection;
import agh.cs.position.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements IMapElement {

    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private final Genes genes;
    private IWorldMap map;
    private final int minEnergyToReproduce;
    private int age = 0;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal (IWorldMap map, Vector2d position, int startEnergy){
        this.map = map;
        this.position = position;
        this.genes = new Genes();
        this.direction = MapDirection.getRandomDirection();
        this.energy = startEnergy;
        this.minEnergyToReproduce = startEnergy/2;
    }

    private Animal (IWorldMap map, Animal parent1, Animal parent2){
        this.map = map;
        this.position = parent1.position;
        this.genes = new Genes (parent1.genes, parent2.genes);
        this.direction = MapDirection.getRandomDirection();
        this.minEnergyToReproduce = parent1.minEnergyToReproduce;

        this.energy = (int) (0.25 * parent1.energy) + (int) (0.25 * parent2.energy);
        parent1.reduceEnergy((int) (0.25 * parent1.energy));
        parent2.reduceEnergy((int) (0.25 * parent2.energy));
    }

    public void move (){
        int index = new Random().nextInt(32);
        this.direction = this.direction.rotation(this.genes.getGeneByIndex(index));
        Vector2d newPosition = this.position.add (this.direction.toUnitVector());
        newPosition = this.map.correctPosition(newPosition);

        this.reduceEnergy(this.map.getMoveEnergy());
        this.age++;

        Vector2d oldPosition = this.position;
        this.position = newPosition;
        this.positionChanged(oldPosition, newPosition);
    }

    public void eatGrass(Grass grass){
        List <Animal> strongestAnimals = this.map.getStrongestAnimals(this.map.chooseAnimals(this.map.objectsAt(grass.getPosition())));
        if(strongestAnimals.size() == 0) return;

        int proteinToShare = grass.getProtein() / strongestAnimals.size();
        for (Animal animal : strongestAnimals){
            animal.energy += proteinToShare;
        }
    }

    public boolean canReproduce(){
        return this.energy >= this.minEnergyToReproduce;
    }

    public void reproduce (Animal parent2){
        if (this.canReproduce() && parent2.canReproduce()){
            Animal children = new Animal(this.map, this, parent2);
            this.map.place(children);
        }
    }

    public boolean isDead (){
        return this.energy <= 0;
    }

    private void reduceEnergy (int energy){
        this.energy -= energy;
    }

    public String toString(){
        if (this.isDead()) return "❌";
        //return this.direction.toString();
        return "\uD83D\uDC3B";
    }

    public void addObserver (IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver (IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    private void positionChanged (Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : this.observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public Vector2d getPosition(){
        return position;
    }

    public int getEnergy(){
        return energy;
    }

    public MapDirection getDirection(){
        return this.direction;
    }

    public int getAge() {
        return this.age;
    }
}
