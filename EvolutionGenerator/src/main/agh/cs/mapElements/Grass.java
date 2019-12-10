package agh.cs.mapElements;

import agh.cs.position.Vector2d;

public class Grass implements IMapElement {

    private Vector2d position;
    private final int protein;

    public Grass(Vector2d position, int plantEnergy){

        this.position = position;
        this.protein = plantEnergy;
    }

    public int getProtein(){
        return protein;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString(){
        return "*";
    }
}
