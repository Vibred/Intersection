// Intersection.java
import java.util.*;
import java.util.concurrent.*;

public class Intersection {
    private TrafficLight northSouthLight;
    private TrafficLight eastWestLight;
    private Queue<Vehicle> northQueue;
    private Queue<Vehicle> southQueue;
    private Queue<Vehicle> eastQueue;
    private Queue<Vehicle> westQueue;

    public Intersection() {
        northSouthLight = new TrafficLight("南北方向");
        eastWestLight = new TrafficLight("东西方向");
        northQueue = new ConcurrentLinkedQueue<>();
        southQueue = new ConcurrentLinkedQueue<>();
        eastQueue = new ConcurrentLinkedQueue<>();
        westQueue = new ConcurrentLinkedQueue<>();
    }

    // 模拟交通信号灯切换和车辆通行
    public void startSimulation(int greenLightDuration, int intervalBetweenVehicles) throws InterruptedException {
        new Thread(northSouthLight).start();
        new Thread(eastWestLight).start();

        while (true) {
            // 随机生成车辆并加入队列
            addVehicleToRandomQueue();
            printIntersectionStatus();

            // 根据信号灯状态放行车辆
            if (northSouthLight.isGreen()) {
                releaseVehicles(northQueue, "北");
                releaseVehicles(southQueue, "南");
            } else {
                releaseVehicles(eastQueue, "东");
                releaseVehicles(westQueue, "西");
            }

            Thread.sleep(intervalBetweenVehicles * 1000);
        }
    }

    // 随机生成车辆并加入相应队列
    private void addVehicleToRandomQueue() {
        Random rand = new Random();
        int direction = rand.nextInt(4);
        int movement = rand.nextInt(3);
        Vehicle vehicle = new Vehicle(direction, movement);

        switch (direction) {
            case Vehicle.NORTH -> northQueue.add(vehicle);
            case Vehicle.SOUTH -> southQueue.add(vehicle);
            case Vehicle.EAST -> eastQueue.add(vehicle);
            case Vehicle.WEST -> westQueue.add(vehicle);
        }
    }

    // 根据信号灯状态放行车辆
    private void releaseVehicles(Queue<Vehicle> queue, String direction) throws InterruptedException {
        if (queue.isEmpty()) {
            return;
        }

        Vehicle vehicle = queue.peek();
        if (vehicle.getMovement() == Vehicle.RIGHT) {
            System.out.println(direction + "方向右转车辆通行: " + vehicle);
            queue.poll();
        } else if (northSouthLight.isGreen()) {
            System.out.println(direction + "方向直行或左转车辆通行: " + vehicle);
            queue.poll();
            Thread.sleep(1000);
        }
    }

    // 打印十字路口当前状态
    private void printIntersectionStatus() {
        System.out.println("北方向车辆数: " + northQueue.size());
        System.out.println("南方向车辆数: " + southQueue.size());
        System.out.println("东方向车辆数: " + eastQueue.size());
        System.out.println("西方向车辆数: " + westQueue.size());
    }
}
