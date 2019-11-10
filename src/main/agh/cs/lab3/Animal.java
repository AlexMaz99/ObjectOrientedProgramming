package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab2.MapDirection;
import agh.cs.lab5.AbstractWorldMap;
import agh.cs.lab5.Grass;
import agh.cs.lab5.GrassField;
import agh.cs.lab5.IMapElement;


import static agh.cs.lab2.MoveDirection.BACKWARD;
import static agh.cs.lab2.MapDirection.NORTH;

public class Animal implements IMapElement {
    private MapDirection direction;
    private Vector2d position;
    private AbstractWorldMap map;

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
                this.moveTo (destination);
                break;
        }
    }

    public void moveTo (Vector2d destination){
        if (map.canMoveTo(destination)){ // there is grass or is empty
            boolean createGrass = false;
            if (map.isOccupied(destination)){ //there is grass
                createGrass = true;
                map.elementsMap.remove(destination);
            }

            map.elementsMap.remove(this.position);
            this.position = destination;
            map.elementsMap.put(this.position, this);
            map.updateCorner (destination);

            if (this.map instanceof GrassField && createGrass){
                Vector2d newPosition = GrassField.generateGrass((GrassField)this.map);
                map.elementsMap.put(newPosition, new Grass(newPosition));
                map.updateCorner(newPosition);
            }
        }
    }
}
