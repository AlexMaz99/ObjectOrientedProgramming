package agh.cs;

import agh.cs.map.IWorldMap;
import agh.cs.map.WorldBuilder;
import agh.cs.map.WorldMap;

public class World {
    public static void main (String [] args) {

        WorldBuilder worldBuilder = new WorldBuilder();
        IWorldMap map = new WorldMap(worldBuilder.mapWidth,
                                    worldBuilder.mapHeight,
                                    worldBuilder.jungleRatio,
                                    worldBuilder.plantEnergy,
                                    worldBuilder.moveEnergy,
                                    worldBuilder.startEnergy,
                                    worldBuilder.startNumberOfAnimals);

        System.out.println(map);
        for (int i = 0; i < 1000; i++){
            map.anotherDay();
        }
    }
}
