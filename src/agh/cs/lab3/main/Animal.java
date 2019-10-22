package agh.cs.lab3.main;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab2.main.MapDirection;

import static agh.cs.lab2.MoveDirection.BACKWARD;
import static agh.cs.lab2.main.MapDirection.NORTH;

public class Animal {
    private MapDirection direction = NORTH;
    private Vector2d position = new Vector2d(2,2);

    public String toString(){

        return this.direction.toString() + this.position.toString();
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
                Vector2d sum = this.position.add(delta);
                if (sum.precedes(new Vector2d(4,4)) && sum.follows(new Vector2d(0,0)))
                    this.position = sum;
                break;
        }
    }
}
