package agh.cs.patterns;

import agh.cs.Board;
import agh.cs.structures.Vector2d;

public class Pulsar extends Board {
    public Pulsar(){
        super(15,15);
        addLivingCell(new Vector2d(4,2));
        addLivingCell(new Vector2d(5,2));
        addLivingCell(new Vector2d(6,2));

        addLivingCell(new Vector2d(4,9));
        addLivingCell(new Vector2d(5,9));
        addLivingCell(new Vector2d(6,9));

        addLivingCell(new Vector2d(2,4));
        addLivingCell(new Vector2d(2,5));
        addLivingCell(new Vector2d(2,6));

        addLivingCell(new Vector2d(2,10));
        addLivingCell(new Vector2d(2,11));
        addLivingCell(new Vector2d(2, 12));

        addLivingCell(new Vector2d(7,4));
        addLivingCell(new Vector2d(7,5));
        addLivingCell(new Vector2d(7,6));

        addLivingCell(new Vector2d(4,7));
        addLivingCell(new Vector2d(5,7));
        addLivingCell(new Vector2d(6,7));

        addLivingCell(new Vector2d(4,14));
        addLivingCell(new Vector2d(5,14));
        addLivingCell(new Vector2d(6,14));

        addLivingCell(new Vector2d(7,10));
        addLivingCell(new Vector2d(7,11));
        addLivingCell(new Vector2d(7,12));

        addLivingCell(new Vector2d(9,4));
        addLivingCell(new Vector2d(9,5));
        addLivingCell(new Vector2d(9,6));

        addLivingCell(new Vector2d(10, 2));
        addLivingCell(new Vector2d(11,2));
        addLivingCell(new Vector2d(12,2));

        addLivingCell(new Vector2d(10, 7));
        addLivingCell(new Vector2d(11,7));
        addLivingCell(new Vector2d(12,7));

        addLivingCell(new Vector2d(10, 9));
        addLivingCell(new Vector2d(11,9));
        addLivingCell(new Vector2d(12,9));

        addLivingCell(new Vector2d(10, 14));
        addLivingCell(new Vector2d(11,14));
        addLivingCell(new Vector2d(12,14));

        addLivingCell(new Vector2d(14, 4));
        addLivingCell(new Vector2d(14,5));
        addLivingCell(new Vector2d(14,6));

        addLivingCell(new Vector2d(14, 10));
        addLivingCell(new Vector2d(14,11));
        addLivingCell(new Vector2d(14,12));

        addLivingCell(new Vector2d(9, 10));
        addLivingCell(new Vector2d(9,11));
        addLivingCell(new Vector2d(9,12));
    }
}
