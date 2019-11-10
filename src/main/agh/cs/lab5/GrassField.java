package agh.cs.lab5;

import agh.cs.lab2.Vector2d;
import agh.cs.lab3.Animal;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    final public int howMuchGrass;
    public Random rand = new Random();

    public GrassField(int howMuchGrass){

        lowerLeft = new Vector2d (0,0);
        upperRight = new Vector2d (0,0);
        this.howMuchGrass = howMuchGrass;

        Vector2d grassPosition;
        for(int i=0; i<howMuchGrass; i++){
            grassPosition = generateGrass(this);
            this.elementsMap.put (grassPosition, new Grass(grassPosition));
            updateCorner(grassPosition);
        }
    }

    @Override
    public boolean place(Animal animal){
        if (objectAt(animal.getPosition()) instanceof Grass){
            Vector2d newPosition = generateGrass(this);
            this.elementsMap.put(newPosition, new Grass (newPosition));
            this.elementsMap.remove(animal.getPosition());
        }
        if(super.place(animal)){
            updateCorner(animal.getPosition());
            return true;
        }
        return false;
    }

    public static Vector2d generateGrass(GrassField map){
        Vector2d grassPosition;
        int x,y;
        do{
            x = new Random().nextInt((int) Math.sqrt (map.howMuchGrass * 10)+1);
            y = new Random().nextInt((int) Math.sqrt (map.howMuchGrass * 10)+1);
            grassPosition = new Vector2d (x,y);
        } while (map.isOccupied(grassPosition));
        return grassPosition;
    }
}
