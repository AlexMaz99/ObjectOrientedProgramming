package agh.cs.lab5;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.MapVisualizer;

import java.util.ArrayList;
import java.util.List;

public class UnboundedMap extends AbstractWorldMap {

    public UnboundedMap (List<Stone> stones){
        this.elements.addAll(stones);
    }
    @Override
    public boolean place(Animal animal){
        if (this.isOccupied(animal.getPosition())) return false;
        elements.add (animal);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer (this);
        Vector2d lowerLeft = this.elements.get(0).getPosition();
        Vector2d upperRight = this.elements.get(0).getPosition();
        for (IMapElement element : elements){
            lowerLeft = lowerLeft.lowerLeft(element.getPosition());
            upperRight = upperRight.upperRight(element.getPosition());
        }
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}
