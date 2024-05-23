// Vehicle.java
public class Vehicle {
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;

    public static final int STRAIGHT = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private int direction;
    private int movement;

    public Vehicle(int direction, int movement) {
        this.direction = direction;
        this.movement = movement;
    }

    public int getDirection() {
        return direction;
    }

    public int getMovement() {
        return movement;
    }

    @Override
    public String toString() {
        String[] directions = {"北", "南", "东", "西"};
        String[] movements = {"直行", "左转", "右转"};
        return directions[direction] + movements[movement];
    }
}
