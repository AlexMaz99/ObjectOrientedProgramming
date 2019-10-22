package agh.cs.lab3.main;

import agh.cs.lab2.MoveDirection;

import java.util.Arrays;

import static agh.cs.lab2.MoveDirection.*;

public class OptionsParsers {
    public static MoveDirection[] parse(String [] args){
        MoveDirection [] directions = new MoveDirection [args.length];
        int counter=0;
        for (String direction : args){
            switch (direction){
                case "f":
                case "forward":
                    directions[counter]=FORWARD;
                    counter++;
                    break;
                case "b":
                case "backward":
                    directions[counter]=BACKWARD;
                    counter++;
                    break;
                case "r":
                case "right":
                    directions[counter]=RIGHT;
                    counter++;
                    break;
                case "l":
                case "left":
                    directions[counter]=LEFT;
                    counter++;
                    break;
            }
        }
        return Arrays.copyOfRange(directions,0,counter);
    }
}
