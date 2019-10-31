package agh.cs.lab5;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.MapVisualizer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {
    /*public List<Animal> animals = new ArrayList<>();
    public List<Stone> stones = new ArrayList<>();*/
    public List<IMapElement> elements = new ArrayList<>();

    @Override
    public void run(MoveDirection[] directions){
        List<Animal> animals = new ArrayList<>();
        for(IMapElement element : elements){
            if(element instanceof Animal)
                animals.add((Animal) element);
        }
        for (int i=0; i<directions.length; i++){
            animals.get(i % animals.size()).move(directions[i]);
        }
    }


    @Override
    public Object objectAt(Vector2d position) {
        for (IMapElement element : elements){
            if (element.getPosition().equals(position))
                return element;
        }
        return null;
    }

    @Override
    public boolean isOccupied(Vector2d position){
        for (IMapElement element : elements){
            if (element.getPosition().equals(position))
                return true;
        }
        return false;
    }

}

