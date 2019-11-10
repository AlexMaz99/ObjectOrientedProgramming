package agh.cs.lab5;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.MapVisualizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap {
    public List<Animal> animals = new ArrayList<>();
    public Map<Vector2d, IMapElement> elementsMap = new HashMap<>();

    public Vector2d lowerLeft;
    public Vector2d upperRight;

    @Override
    public void run(MoveDirection[] directions){
        for (int i=0; i<directions.length; i++){
            this.animals.get(i % animals.size()).move(directions[i]);
        }
    }


    @Override
    public Object objectAt(Vector2d position) {
        if (this.elementsMap.containsKey(position))
            return this.elementsMap.get(position);
        return null;
    }

    @Override
    public boolean isOccupied(Vector2d position){

        return this.elementsMap.containsKey(position);
    }

    @Override
    public boolean place(Animal animal){
        if (!this.canMoveTo(animal.getPosition()))
            throw new IllegalArgumentException("Position: " + animal.getPosition().toString() + " is occupied");

        this.animals.add (animal);
        this.elementsMap.put(animal.getPosition(), animal);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position){

        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(this.lowerLeft, this.upperRight);
    }

    public void updateCorner (Vector2d position){
        this.lowerLeft = this.lowerLeft.lowerLeft(position);
        this.upperRight = this.upperRight.upperRight(position);
    }

}

