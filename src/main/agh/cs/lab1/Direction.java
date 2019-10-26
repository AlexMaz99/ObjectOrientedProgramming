package agh.cs.lab1;

public enum Direction {

    FORWARD,
    BACK,
    LEFT,
    RIGHT;

    public static Direction fromString(String str){
        switch(str){
            case "f": return FORWARD;
            case "b": return BACK;
            case "r": return RIGHT;
            case "l": return LEFT;
            default: return null;
        }
    }
}

