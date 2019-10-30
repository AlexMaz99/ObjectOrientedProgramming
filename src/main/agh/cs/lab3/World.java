package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.RectangularMap;
import agh.cs.lab5.Stone;
import agh.cs.lab5.UnboundedMap;

import java.util.ArrayList;
import java.util.List;


public class World {
    public static void main(String[] args) {
        MoveDirection[] directions = new OptionsParsers().parse(args);
        List<Stone> stones = new ArrayList<>();
        /*IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map));
        map.place(new Animal(map, new Vector2d(3, 4)));*/

        stones.add(new Stone( new Vector2d (-4,-4)));
        stones.add(new Stone( new Vector2d (7,7)));
        stones.add(new Stone( new Vector2d (3,6)));
        stones.add(new Stone( new Vector2d (2,0)));

        IWorldMap map = new UnboundedMap(stones);
        map.place(new Animal(map));
        map.place(new Animal(map, new Vector2d(3, 4)));

        System.out.println(map.toString());
        map.run(directions);

        System.out.println(map.toString());
    }
}
