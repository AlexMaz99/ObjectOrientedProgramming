package agh.cs.patterns;

import agh.cs.Board;
import agh.cs.structures.Vector2d;

public class House extends Board {
    public House(){
        super(100,100);
        addLivingCell(new Vector2d(19,32));
        addLivingCell(new Vector2d(19,33));
        addLivingCell(new Vector2d(19,34));

        addLivingCell(new Vector2d(20,31));
        addLivingCell(new Vector2d(21,31));
        addLivingCell(new Vector2d(21,32));

        addLivingCell(new Vector2d(20,35));
        addLivingCell(new Vector2d(21,35));
        addLivingCell(new Vector2d(21,34));
    }
}
