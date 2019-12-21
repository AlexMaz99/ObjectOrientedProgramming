package agh.cs.structures;

import java.util.Random;

public enum MapDirection {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

    public String toString(){
        switch(this){
            case EAST: return "\u21d2";
            case NORTH: return "\u21d1";
            case SOUTH: return "\u21d3";
            case WEST: return "\u21d0";
            case NORTHEAST: return "\u21d7";
            case NORTHWEST: return "\u21d6";
            case SOUTHEAST: return "\u21d8";
            case SOUTHWEST: return "\u21d9";
            default: return null;
        }
    }
    public MapDirection rotation(int rotationNumber){
        if (rotationNumber < 0 || rotationNumber > 7) throw new IllegalArgumentException("The rotation number should be a number between 0 and 7");
        return MapDirection.values()[(this.ordinal() + rotationNumber) % MapDirection.values().length];
    }
    public Vector2d toUnitVector(){
        switch(this){
            case EAST: return new Vector2d(1,0);
            case SOUTH: return new Vector2d(0,-1);
            case WEST: return new Vector2d(-1,0);
            case NORTH: return new Vector2d(0,1);
            case NORTHEAST: return new Vector2d(1,1);
            case NORTHWEST: return new Vector2d(-1,1);
            case SOUTHEAST: return new Vector2d(1,-1);
            case SOUTHWEST: return new Vector2d(-1,-1);
            default: return null;
        }
    }
    public static MapDirection getRandomDirection(){
        return MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
    }
}
