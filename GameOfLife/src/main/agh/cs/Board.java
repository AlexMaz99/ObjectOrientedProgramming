package agh.cs;

import agh.cs.structures.Cell;
import agh.cs.structures.Vector2d;

import java.util.*;

public class Board {

    private Vector2d lowerLeft, upperRight;
    private List<Integer> survivalRules;
    private List<Integer> birthRules;
    private HashMap<Vector2d, Cell> aliveCells;

    public Board (int width, int height){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width, height);
        this.aliveCells = new HashMap<>();
        this.survivalRules = new ArrayList<>();
        this.birthRules = new ArrayList<>();
        this.survivalRules.add(2);
        this.survivalRules.add(3);
        this.birthRules.add(3);
        this.updateNeighbours();
    }
    public Board (int width, int height, List<Integer> survivalRules, List<Integer> birthRules){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width, height);
        this.aliveCells = new HashMap<>();
        this.survivalRules = new ArrayList<>();
        this.birthRules = new ArrayList<>();
        this.survivalRules.addAll(survivalRules);
        this.birthRules.addAll(birthRules);
        this.updateNeighbours();
    }


    public void day(){
        this.updateNeighbours();
        List<Cell> deadCells = new ArrayList<>();
        Collection<Cell> livingCells = this.aliveCells.values();

        for (Cell cell: livingCells){
            if (!this.survivalRules.contains(cell.getNeighbours())){
                deadCells.add(cell);
            }
        }
        Set<Vector2d> cellsPosition = new HashSet<>();

        for (Cell cell: livingCells){
            cellsPosition.addAll(cell.getPosition().neighbours());
        }

        List<Vector2d> correctCandidates = new ArrayList<>();

        for (Vector2d position: cellsPosition){
            if (insideBoard(position) && !this.isAliveCell(position) && this.birthRules.contains(this.countLivingNeighbours(position)))
                correctCandidates.add(position);
        }

        for (Vector2d position: correctCandidates){
            this.addLivingCell(position);
        }
        for (Cell deadCell: deadCells){
            this.removeDeadCell(deadCell.getPosition());
        }
        deadCells.clear();
    }

    public int countLivingNeighbours(Vector2d position){
        int livingNeighbours = 0;
        for (Vector2d neighbour: position.neighbours()){
            if (insideBoard(neighbour) && this.isAliveCell(neighbour)){
                livingNeighbours++;
            }
        }
        return livingNeighbours;

    }
    public void updateNeighbours(){
        for (Cell livingCell: this.aliveCells.values()) livingCell.updateNeighbours();
    }

    public void addSurvivalRule(Integer rule){
        if (!this.survivalRules.contains(rule))
            this.survivalRules.add(rule);
    }
    public void removeSurvivalRule(Integer rule){
        this.survivalRules.remove(rule);
    }
    public void addLivingCell (Vector2d position){
        this.aliveCells.put(position, new Cell(position, this));
    }
    public void removeDeadCell (Vector2d position){
        Cell cell = this.aliveCells.get(position);
        cell.changeStates();
        this.aliveCells.remove(position);
    }

    public boolean isAliveCell(Vector2d position){
        return this.aliveCells.get(position)!=null;
    }
    public boolean insideBoard(Vector2d position){
        return position.precedes(this.upperRight) && position.follows(this.lowerLeft);
    }

    public void start(){
        //System.out.print(toString());
        try {
            Thread.sleep(50);
            day();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int getWidth(){
        return this.upperRight.x - this.lowerLeft.x;
    }
    public int getHeight(){
        return this.upperRight.y - this.lowerLeft.y;
    }

    public boolean isAnyCellAlive(){
        return !this.aliveCells.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int i=1; i<=this.upperRight.x; i++){
            for (int j=1; j<=this.upperRight.y; j++){
                if (this.aliveCells.containsKey(new Vector2d(i,j))){
                    //boardString.append(this.aliveCells.get(new Vector2d(i,j)).toString());
                    boardString.append("■");
                }
                else boardString.append("□");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

}
