package agh.cs.lab4;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab5.AbstractWorldMap;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap {
    Vector2d lowerLeft;
    Vector2d upperRight;


    public RectangularMap(int width, int height) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && position.precedes(this.upperRight) && position.follows(this.lowerLeft);
    }
    @Override
    public boolean place(Animal animal){
        if (this.isOccupied(animal.getPosition()) || animal.getPosition().precedes(this.lowerLeft) || animal.getPosition().follows(this.upperRight)) return false;
        elements.add (animal);
        return true;
    }

    @Override
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer (this);
        return mapVisualizer.draw(this.lowerLeft, this.upperRight);
    }

}
