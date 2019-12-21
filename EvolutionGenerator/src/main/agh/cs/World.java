package agh.cs;

import agh.cs.map.WorldSimulator;
import agh.cs.visualization.EvolutionVisualizer;
import agh.cs.map.JSONReader;

public class World {
    public static void main (String [] args) {

        JSONReader jsonReader = new JSONReader();
        WorldSimulator worldSimulator = new WorldSimulator(jsonReader);
        EvolutionVisualizer evolutionVisualizer = new EvolutionVisualizer(worldSimulator);
        evolutionVisualizer.startAnimation();

    }
}
