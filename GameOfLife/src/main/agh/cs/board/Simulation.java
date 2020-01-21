package agh.cs.board;

import agh.cs.data.JSONReader;
import agh.cs.patterns.*;
import agh.cs.structures.Vector2d;
import java.util.List;

public class Simulation {
    private Board board;

    public Simulation(JSONReader jsonReader){
        String pattern = jsonReader.getPattern();
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
                if (pattern.length() %2 == 1)
                    throw new IllegalArgumentException("There is no pattern called " + pattern);
                List<Integer> survivalRules = jsonReader.getSurvivalRules();
                List<Integer> birthRules = jsonReader.getBirthRules();

                this.board = new Board(jsonReader.getWidth(), jsonReader.getHeight(), survivalRules, birthRules);
                for (int i =0; i<pattern.length() - 1; i+=2) {
                    int x = Character.getNumericValue(pattern.charAt(i));
                    int y = Character.getNumericValue(pattern.charAt(i+1));
                    if (new Vector2d(x,y).follows(new Vector2d(jsonReader.getWidth(), jsonReader.getHeight())))
                        throw new IllegalArgumentException("Position is out of board");
                    this.board.addLivingCell(new Vector2d(x,y));
                }
        }
    }
    public Board getBoard(){
        return this.board;
    }
}
