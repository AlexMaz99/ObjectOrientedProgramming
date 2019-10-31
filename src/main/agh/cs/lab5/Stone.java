package agh.cs.lab5;

import agh.cs.lab2.Vector2d;

public class Stone implements IMapElement{
    public Vector2d position;
    public Stone(Vector2d position){
        this.position=position;
    }
    public Vector2d getPosition(){

        return this.position;
    }
    public String toString(){

        return "s";
    }
}
