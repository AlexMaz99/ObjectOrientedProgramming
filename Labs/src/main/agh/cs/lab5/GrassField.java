package agh.cs.lab5;

import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import agh.cs.lab4.MapVisualizer;
import agh.cs.lab7.MapBoundary;

import java.util.Random;

public class GrassField extends AbstractWorldMap {
    final private int howMuchGrass;
    private MapBoundary mapBoundary = new MapBoundary();

    public GrassField(int howMuchGrass){
        this.howMuchGrass = howMuchGrass;
        Vector2d grassPosition;

        for(int i=0; i<howMuchGrass; i++){
            grassPosition = createGrass(this);
            Grass grass = new Grass (grassPosition);
            this.elementsMap.put (grassPosition, grass);
            mapBoundary.addObject(grass);
        }
    }

    @Override
    public boolean place(Animal animal){
        this.relocateGrass(animal.getPosition());
        if(super.place(animal)){
            mapBoundary.addObject(animal);
            animal.addObserver(mapBoundary);
            return true;
        }
        return false;
    }

    public void relocateGrass(Vector2d position){
        IMapElement element = this.objectAt (position);
        if (element instanceof Grass){
            Vector2d newPosition = GrassField.createGrass(this);
            Grass grass = new Grass (newPosition);
            this.elementsMap.put(newPosition, grass);
            mapBoundary.deleteObject(element.getPosition());
            mapBoundary.addObject(grass);
            this.elementsMap.remove(position);
        }
    }

    public static Vector2d createGrass(GrassField map){
        Vector2d grassPosition;
        int x,y;
        do{
            x = new Random().nextInt((int) Math.sqrt (map.howMuchGrass * 10)+1);
            y = new Random().nextInt((int) Math.sqrt (map.howMuchGrass * 10)+1);
            grassPosition = new Vector2d (x,y);
        } while (map.isOccupied(grassPosition));
        return grassPosition;
    }

    @Override
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);

        Vector2d lowerLeft = new Vector2d(mapBoundary.getLeftBoundary(), mapBoundary.getLowerBoundary());
        Vector2d upperRight = new Vector2d(mapBoundary.getRightBoundary(), mapBoundary.getUpperBoundary());
        return mapVisualizer.draw(lowerLeft, upperRight);
    }
}
