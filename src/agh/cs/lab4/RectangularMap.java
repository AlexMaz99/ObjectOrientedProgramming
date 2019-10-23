package agh.cs.lab4;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.main.Animal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class RectangularMap implements IWorldMap {
    int height;
    int width;

    private List<Animal> animals=new ArrayList();
    private Hashtable <String, Animal> usedMapCoords = new Hashtable <String, Animal>();

    public RectangularMap(int width, int height){
        this.width=width;
        this.height=height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(new Vector2d(this.width, this.height)) && position.follows(new Vector2d(0,0));
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public void run(MoveDirection[] directions) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }
}
