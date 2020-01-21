package agh.cs.patterns;

import agh.cs.board.Board;
import agh.cs.structures.Vector2d;

public class Pentadecathlon extends Board {
    public Pentadecathlon(){
        super(18, 11);
        addLivingCell(new Vector2d(5, 6));
        addLivingCell(new Vector2d(6, 6));

        addLivingCell(new Vector2d(8, 6));
        addLivingCell(new Vector2d(9, 6));
        addLivingCell(new Vector2d(10, 6));
        addLivingCell(new Vector2d(11, 6));

        addLivingCell(new Vector2d(13, 6));
        addLivingCell(new Vector2d(14, 6));

        addLivingCell(new Vector2d(7, 5));
        addLivingCell(new Vector2d(7, 7));

        addLivingCell(new Vector2d(12, 5));
        addLivingCell(new Vector2d(12, 7));
    }
}
