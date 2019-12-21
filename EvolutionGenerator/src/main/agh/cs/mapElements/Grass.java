package agh.cs.mapElements;

import agh.cs.structures.Vector2d;

public class Grass implements IMapElement {

    private Vector2d position;
    private final int protein;

    public Grass(Vector2d position, int plantEnergy){
        this.position = position;
        this.protein = plantEnergy;
    }

    int getProtein(){
        return protein;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString(){
        return "☘";
    }
}
