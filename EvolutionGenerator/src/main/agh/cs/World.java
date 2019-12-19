package agh.cs;

import agh.cs.map.IWorldMap;
import agh.cs.map.Visualization;
import agh.cs.map.JSONReader;
import agh.cs.map.WorldMap;

public class World {
    public static void main (String [] args) {

        JSONReader jsonReader = new JSONReader();
        IWorldMap map = new WorldMap(jsonReader.getMapWidth(),
                                    jsonReader.getMapHeight(),
                                    jsonReader.getJungleRatio(),
                                    jsonReader.getPlantEnergy(),
                                    jsonReader.getMoveEnergy(),
                                    jsonReader.getStartEnergy(),
                                    jsonReader.getStartNumberOfAnimals());
        Visualization visualization = new Visualization(map);
        visualization.startAnimation();

        /*System.out.println(map);
        for (int i = 0; i < 1000; i++){
            map.anotherDay();
        }*/
    }
}
