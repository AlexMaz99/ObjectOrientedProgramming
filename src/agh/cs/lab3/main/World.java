package agh.cs.lab3.main;

import agh.cs.lab2.MoveDirection;

import static agh.cs.lab3.main.OptionsParsers.parse;

public class World {
    public static void main(String[] args) {
        Animal dog = new Animal();
        System.out.println(dog);
        MoveDirection[] positions = parse (args);
        for (int i=0; i<positions.length; i++){
                dog.move(positions[i]);
                System.out.println(dog);
        }
    }
}
