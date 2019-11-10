package agh.cs.lab4;

import agh.cs.lab2.Vector2d;
import agh.cs.lab5.AbstractWorldMap;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(this.upperRight) && position.follows(this.lowerLeft) && super.canMoveTo(position));
    }

    @Override
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer (this);
        return mapVisualizer.draw(this.lowerLeft, this.upperRight);
    }

}
