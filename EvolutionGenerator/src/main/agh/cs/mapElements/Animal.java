package agh.cs.mapElements;

import agh.cs.map.IWorldMap;
import agh.cs.map.IPositionChangeObserver;
import agh.cs.position.MapDirection;
import agh.cs.position.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class Animal implements IMapElement {

    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private final Genes genes;
    private IWorldMap map;
    private final int minEnergyToReproduce;
    private final int moveEnergy;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal (IWorldMap map, Vector2d position, int startEnergy){
        this.map = map;
        this.position = position;
        this.genes = new Genes();
        this.direction = MapDirection.getRandomDirection();
        this.energy = startEnergy;
        this.minEnergyToReproduce = startEnergy/2;
        this.moveEnergy = this.map.getMoveEnergy();
    }

    private Animal (IWorldMap map, Animal parent1, Animal parent2){
        this.map = map;
        this.position = parent1.position;
        this.genes = new Genes (parent1.genes, parent2.genes);
        this.direction = MapDirection.getRandomDirection();
        this.moveEnergy = this.map.getMoveEnergy();

        this.energy = (int) (0.25 * parent1.energy) + (int) (0.25 * parent2.energy);
        parent1.energy = (int) (0.75 * parent1.energy);
        parent2.energy = (int) (0.75 * parent2.energy);
        this.minEnergyToReproduce = this.energy/2;
    }

    public void move (){
        int index = new Random().nextInt(32);
        this.direction = this.direction.rotation(this.genes.getGeneByIndex(index));
        Vector2d newPosition = this.position.add (this.direction.toUnitVector());
        newPosition = this.map.correctPosition(newPosition);

        this.reduceEnergy(this.moveEnergy);

        Vector2d oldPosition = this.getPosition();
        this.position = newPosition;
        this.positionChanged(oldPosition, newPosition);
    }

    public boolean eatGrass(Grass grass){ // TODO: Correct
        List <IMapElement> elements = this.map.objectsAt(grass.getPosition());
        int maxEnergy = 0;
        for (IMapElement element: elements){
            if (element instanceof Animal){
                maxEnergy = max (maxEnergy, ((Animal) element).getEnergy());
            }
        }
        if(maxEnergy==0) return false;
        List <Animal> animalWithSameEnergy = new ArrayList<>();
        for (IMapElement animal: elements){
            if (animal instanceof Animal && ((Animal) animal).energy==maxEnergy){
                animalWithSameEnergy.add((Animal) animal);
            }
        }
        int proteinToShare = grass.getProtein() / animalWithSameEnergy.size();
        for (Animal animal : animalWithSameEnergy){
            animal.energy += proteinToShare;
        }
        return true;
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
        if (this.isDead()) return "X";
        return this.direction.toString();
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
}
