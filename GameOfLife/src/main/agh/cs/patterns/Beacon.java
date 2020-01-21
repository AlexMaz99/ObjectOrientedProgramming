package agh.cs.patterns;

import agh.cs.board.Board;
import agh.cs.structures.Vector2d;

public class Beacon extends Board {
    public Beacon(){
        super(6,6);
        addLivingCell(new Vector2d(2,2));
        addLivingCell(new Vector2d(3,2));
        addLivingCell(new Vector2d(2,3));

        addLivingCell(new Vector2d(5,4));
        addLivingCell(new Vector2d(5,5));
        addLivingCell(new Vector2d(4,5));
    }
}
