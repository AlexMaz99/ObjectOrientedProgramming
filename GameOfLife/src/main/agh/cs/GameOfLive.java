package agh.cs;


import agh.cs.board.Simulation;
import agh.cs.data.JSONReader;
import agh.cs.visualization.Visualization;

public class GameOfLive {
    public static void main (String [] args) {

       Simulation simulation = new Simulation(new JSONReader());
       Visualization visualization = new Visualization(simulation.getBoard());
       visualization.startAnimation();

    }
}
