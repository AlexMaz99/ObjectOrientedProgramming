package agh.cs.lab2;

public enum MapDirection {
    NORTH, SOUTH, WEST, EAST;
    public String toString(){
        switch(this){
            case EAST: return ">";
            case NORTH: return "^";
            case SOUTH: return "v";
            case WEST: return "<";
            default: return null;
        }
    }
    public MapDirection next(){
        switch(this){
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            case NORTH: return EAST;
            default: return null;
        }
    }
    public MapDirection previous(){
        switch(this){
            case EAST: return NORTH;
            case SOUTH: return EAST;
            case WEST: return SOUTH;
            case NORTH: return WEST;
            default: return null;
        }
    }
    public Vector2d toUnitVector(){
        switch(this){
            case EAST: return new Vector2d(1,0);
            case SOUTH: return new Vector2d(0,-1);
            case WEST: return new Vector2d(-1,0);
            case NORTH: return new Vector2d(0,1);
            default: return null;
        }
    }

}
