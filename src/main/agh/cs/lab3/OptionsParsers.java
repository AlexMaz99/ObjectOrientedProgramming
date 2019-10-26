package agh.cs.lab3;

import agh.cs.lab2.MoveDirection;

import java.util.Arrays;


public class OptionsParsers {
    /*public OptionsParsers(){

    }*/
    public static MoveDirection[] parse(String [] args){
        MoveDirection [] directions = new MoveDirection [args.length];
        int counter=0;
        MoveDirection tmp;
        for (String direction : args){
            tmp=MoveDirection.fromString(direction);
            if (tmp!=null){
                directions[counter]=tmp;
                counter++;
            }
        }
        return Arrays.copyOfRange(directions,0,counter);
    }
}
