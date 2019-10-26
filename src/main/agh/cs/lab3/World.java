package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab4.IWorldMap;
import agh.cs.lab4.RectangularMap;


public class World {
    public static void main(String[] args) {
        MoveDirection[] directions = new OptionsParsers().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map));
        map.place(new Animal(map, new Vector2d(3, 4)));
        System.out.println(map.toString());
        map.run(directions);

        System.out.println(map.toString());
    }
}
