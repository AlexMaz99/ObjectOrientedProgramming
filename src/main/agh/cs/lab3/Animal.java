package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab2.MapDirection;
import agh.cs.lab5.AbstractWorldMap;
import agh.cs.lab5.GrassField;
import agh.cs.lab5.IMapElement;
import agh.cs.lab7.IPositionChangeObserver;


import java.util.ArrayList;
import java.util.List;

import static agh.cs.lab2.MoveDirection.BACKWARD;
import static agh.cs.lab2.MapDirection.NORTH;

public class Animal implements IMapElement {
    private MapDirection direction;
    private Vector2d position;
    private AbstractWorldMap map;

    private List <IPositionChangeObserver> observers = new ArrayList<>();

    public Animal (AbstractWorldMap map){
        this(map, new Vector2d(2,2));
    }

    public Animal (AbstractWorldMap map, Vector2d initialPosition){
        this.map=map;
        this.position=initialPosition;
        this.direction=NORTH;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public String toString(){

        return this.direction.toString();
    }
    public void move (MoveDirection direction){
        switch(direction){
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case FORWARD:
            case BACKWARD:
                Vector2d delta = this.direction.toUnitVector();
                if (direction == BACKWARD) delta=delta.opposite();
                Vector2d destination = this.position.add(delta);

                if (this.map.canMoveTo(destination)){ // grass or empty
                    if (map instanceof GrassField)
                        ((GrassField) map).relocateGrass(destination);
                    this.positionChanged(this.getPosition(), destination);
                    this.position = destination;
                }
                break;
        }
    }

    public void addObserver (IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver (IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    public void positionChanged (Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : this.observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
