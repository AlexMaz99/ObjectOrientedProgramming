package agh.cs.lab2;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2d {
    final public int x;
    final public int y;


    public Vector2d(int x, int y) {
        this.x=x;
        this.y=y;
    }
    public String toString(){

        return "(" + this.x + "," + this.y + ")";
    }
    public boolean precedes (Vector2d other){

        return this.x<=other.x && this.y<=other.y;
    }
    public boolean follows (Vector2d other){

        return this.x>=other.x && this.y>=other.y;
    }
    public Vector2d upperRight(Vector2d other){
        int x,y;
        x=max(this.x, other.x);
        y=max(this.y, other.y);
        return new Vector2d(x,y);
    }
    public Vector2d lowerLeft(Vector2d other){
        int x,y;
        x=min(this.x, other.x);
        y=min(this.y, other.y);
        return new Vector2d(x,y);
    }
    public Vector2d add(Vector2d other){

        return new Vector2d (this.x + other.x, this.y + other.y);
    }
    public Vector2d subtract(Vector2d other){

        return new Vector2d (this.x - other.x, this.y - other.y);
    }
    public boolean equals(Object other){
        if (this == other) return true;
        if(!(other instanceof Vector2d)) return false;
        Vector2d that = (Vector2d) other;
        return this.x==that.x && this.y==that.y;
    }
    public Vector2d opposite(){
        return new Vector2d (-this.x, -this.y);
    }
}
