package agh.cs;

import agh.cs.map.IWorldMap;
import agh.cs.map.WorldMap;

public class World {
    public static void main (String [] args) {

        IWorldMap map = new WorldMap(10, 10, 0.4, 3, 1, 20);
        ((WorldMap) map).placeFirstAnimals(10);
        System.out.println(map);
        for (int i = 0; i < 20; i++) {
            ((WorldMap) map).anotherDay();


        }
    }
}
