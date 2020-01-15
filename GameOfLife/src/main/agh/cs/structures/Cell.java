package agh.cs.structures;

import agh.cs.Board;

public class Cell {
    private Vector2d position;
    private State state;
    private int neighbours;
    protected Board board;

    public Cell (Vector2d position, Board board){
        this.position = position;
        this.board = board;
        this.state = State.ALIVE;
        this.neighbours = 0;
    }

    public void changeStates(){
        this.state = this.state.changeState();
    }

    public void updateNeighbours(){
        resetNeighbours();

        for (Vector2d position: this.position.neighbours()){
            if (this.board.insideBoard(position) && this.board.isAliveCell(position))
                this.neighbours ++;
        }
    }

    public void resetNeighbours(){
        this.neighbours = 0;
    }
    public State getState(){
        return this.state;
    }
    public Vector2d getPosition(){
        return this.position;
    }
    public int getNeighbours(){
        return this.neighbours;
    }
    public String toString(){
        if (this.state == State.ALIVE) return "o";
        else return " ";
    }
    public void setStart(){
        this.state = State.ALIVE;
    }
}
