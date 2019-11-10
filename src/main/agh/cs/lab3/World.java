package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;
import agh.cs.lab2.Vector2d;
import agh.cs.lab5.AbstractWorldMap;
import agh.cs.lab5.GrassField;


import static java.lang.System.exit;


public class World {
    public static void main(String[] args) {
        try {
            MoveDirection[] directions = new OptionsParsers().parse(args);

            AbstractWorldMap map = new GrassField(10);
            map.place(new Animal(map));
            map.place(new Animal(map, new Vector2d(3, 4)));
            //map.place(new Animal(map, new Vector2d(3, 4)));

            System.out.println(map.toString());
            map.run(directions);

            System.out.println(map.toString());
        }   catch (IllegalArgumentException ex){
            System.out.println(ex);
            exit(0);
        }
    }
}
