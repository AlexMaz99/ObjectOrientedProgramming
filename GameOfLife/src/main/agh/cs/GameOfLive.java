package agh.cs;

public class GameOfLive {
    public static void main (String [] args) {

        Simulation simulation = new Simulation("house");
        Visualization visualization = new Visualization(simulation.getBoard());
        visualization.startAnimation();

    }
}
