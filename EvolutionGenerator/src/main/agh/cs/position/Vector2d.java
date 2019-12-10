package agh.cs.position;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes (Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows (Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other){
        int newX, newY;
        newX = max(this.x, other.x);
        newY = max(this.y, other.y);
        return new Vector2d(newX, newY);
    }

    public Vector2d lowerLeft(Vector2d other){
        int newX, newY;
        newX = min(this.x, other.x);
        newY = min(this.y, other.y);
        return new Vector2d(newX,newY);
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
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite(){
        return new Vector2d (-this.x, -this.y);
    }


    @Override
    public int hashCode(){
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}
