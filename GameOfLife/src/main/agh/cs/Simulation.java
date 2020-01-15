package agh.cs;

import agh.cs.patterns.*;

import java.util.List;

public class Simulation {
    private Board board;

    public Simulation(String pattern){
        switch (pattern){
            case "Beacon":
            case "beacon":
                this.board = new Beacon();
                break;
            case "Blinker":
            case "blinker":
                this.board = new Blinker();
                break;
            case "Pulsar":
            case "pulsar":
                this.board = new Pulsar();
                break;
            case "Pentadecathlon":
            case "pentadecathlon":
                this.board = new Pentadecathlon();
                break;
            case "House":
            case "house":
                this.board = new House();
                break;
            default:
                throw new IllegalArgumentException("There is no pattern called " + pattern);
        }
    }
    public Simulation(int width, int height, List<Integer> survivalRules, List<Integer> birthRules){
        this.board = new Board(width, height, survivalRules, birthRules);
    }
    public Board getBoard(){
        return this.board;
    }
}
