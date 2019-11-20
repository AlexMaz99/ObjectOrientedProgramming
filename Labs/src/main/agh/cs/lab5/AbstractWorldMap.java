package agh.cs.lab5;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab7.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected List<Animal> animals = new ArrayList<>();
    protected Map<Vector2d, IMapElement> elementsMap = new HashMap<>();


    @Override
    public void run(MoveDirection[] directions){
        for (int i=0; i<directions.length; i++){
            this.animals.get(i % animals.size()).move(directions[i]);
        }
    }

    public List<Animal> getAnimals (){
        return animals;
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        return this.elementsMap.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position){
        return this.elementsMap.containsKey(position);
    }

    @Override
    public boolean place(Animal animal){
        if (!this.canMoveTo(animal.getPosition()))
            throw new IllegalArgumentException("Position: " + animal.getPosition().toString() + " is occupied");

        animal.addObserver(this);
        this.animals.add (animal);
        this.elementsMap.put(animal.getPosition(), animal);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position){
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public void positionChanged (Vector2d oldPosition, Vector2d newPosition){
        Animal animal = (Animal)this.elementsMap.get(oldPosition);
        this.elementsMap.remove(oldPosition);
        this.elementsMap.put(newPosition, animal);
    }
}

