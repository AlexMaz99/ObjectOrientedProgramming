package agh.cs.lab4;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    int height;
    int width;

    public List<Animal> animals=new ArrayList<>();

    public RectangularMap(int width, int height){
        this.width=width;
        this.height=height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && position.precedes(new Vector2d(this.width, this.height)) && position.follows(new Vector2d(0,0));
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
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals){
            if (animal.getPosition().equals(position))
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
        return null;
    }

    @Override
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer (this);
        return mapVisualizer.draw(new Vector2d(0,0), new Vector2d(this.width, this.height));
    }
}
