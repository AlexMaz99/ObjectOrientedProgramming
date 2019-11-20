package agh.cs.lab1;

public class CarSystem {
    private static void run(Direction[] args){
        for (Direction direction : args){
            switch(direction){
                case FORWARD:
                    System.out.println("Car is going forward");
                    break;
                case LEFT:
                    System.out.println("Car is going left");
                    break;
                case RIGHT:
                    System.out.println("Car is going right");
                    break;
                case BACK:
                    System.out.println("Car is going backward");
                    break;
            }
        }
    }

    public static void main (String[] args){

        Direction[] directions = new Direction [args.length];
        for(int i = 0; i < args.length; i++){
            directions[i] = Direction.fromString(args[i]);
        }
        System.out.println("Start");
        run(directions);
        System.out.println("Finish");
    }
}
