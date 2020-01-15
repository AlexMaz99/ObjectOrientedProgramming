package agh.cs.structures;

public enum State {
    ALIVE, DEAD;
    public State changeState(){
        return (this == ALIVE) ? DEAD : ALIVE;
    }
}
