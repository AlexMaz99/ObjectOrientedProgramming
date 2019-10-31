package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab2.MapDirection;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab5.IMapElement;

import static agh.cs.lab2.MoveDirection.BACKWARD;
import static agh.cs.lab2.MapDirection.NORTH;

public class Animal implements IMapElement {
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;

    public Animal (IWorldMap map){
        this(map, new Vector2d(2,2));
    }

    public Animal (IWorldMap map, Vector2d initialPosition){
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
                Vector2d newPosition = this.position.add(delta);
                if (map.canMoveTo(newPosition))
                    this.position = newPosition;
                break;
        }
    }
}
