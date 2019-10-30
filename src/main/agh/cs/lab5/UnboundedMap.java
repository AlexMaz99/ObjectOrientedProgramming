package agh.cs.lab5;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.MapVisualizer;

import java.util.ArrayList;
import java.util.List;

public class UnboundedMap implements IWorldMap {

    public List<Stone> stones;
    public List<Animal> animals=new ArrayList<>();
    Vector2d lowerLeft;
    Vector2d upperRight;

    public UnboundedMap (List<Stone> stones){
        this.stones=stones;
        this.lowerLeft=new Vector2d(0,0);
        this.upperRight=new Vector2d(0,0);
        for (Stone stone : stones){
            updateMap(stone.getPosition());
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (this.isOccupied(animal.getPosition())) return false;
        animals.add (animal);
        return true;
    }

    @Override
    public void run(MoveDirection[] directions) {
        int animalsSize = animals.size();
        for (int i=0; i<directions.length; i++){
            animals.get(i % animalsSize).move(directions[i]);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position){
        for (Animal animal : animals){
            if (animal.getPosition().equals(position))
                return true;
        }
        for (Stone stone : stones){
            if (stone.getPosition().equals(position))
                return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals){
            if (animal.getPosition().equals(position))
                return animal;
        }
        for (Stone stone : stones){
            if (stone.getPosition().equals(position))
                return stone;
        }
        return null;
    }
    private void updateMap (Vector2d position){
        this.lowerLeft = this.lowerLeft.lowerLeft(position);
        this.upperRight = this.upperRight.upperRight(position);
    }

    @Override
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer (this);
        return mapVisualizer.draw(this.lowerLeft, this.upperRight);
    }
}
