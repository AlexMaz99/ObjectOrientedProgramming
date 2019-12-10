package agh.cs;

import agh.cs.map.IWorldMap;
import agh.cs.map.WorldMap;

public class World {
    public static void main (String [] args){

        IWorldMap map = new WorldMap(20,20,0.4,3, 1, 10);
        ((WorldMap) map).placeFirstAnimals(3);
        System.out.println(map);
        for (int i=0; i<10; i++){
            ((WorldMap)map).anotherDay();

        }

    }

}
