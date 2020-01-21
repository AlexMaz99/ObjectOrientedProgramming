package agh.cs.patterns;

import agh.cs.board.Board;
import agh.cs.structures.Vector2d;

public class Blinker extends Board {
    public Blinker(){
        super(3,3);
        addLivingCell(new Vector2d(1,2));
        addLivingCell(new Vector2d(2,2));
        addLivingCell(new Vector2d(3,2));
    }
}
