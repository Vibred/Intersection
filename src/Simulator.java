import java.util.*;
import java.util.concurrent.*;

public class Simulator {
    private static final String[] DIRECTIONS = {"南向", "北向", "东向", "西向"};
    private Map<String, Queue<Car>> lanes = new HashMap<>();
    private TrafficLightControl lightControl = new TrafficLightControl();
    private Random random = new Random();

    public Simulator() {
        for (String direction : DIRECTIONS) {
            lanes.put(direction + "左转", new LinkedList<>());
            lanes.put(direction + "直行", new LinkedList<>());
            lanes.put(direction + "右转", new LinkedList<>());
        }
    }

    // 随机生成来车并放入车道队列
    public void generateCars() {
        int carCount = random.nextInt(25) + 1; // 修改生成随机数的范围为1到25辆车
        for (int i = 0; i < carCount; i++) {
            String from = DIRECTIONS[random.nextInt(4)];
            String to;
            do {
                to = DIRECTIONS[random.nextInt(4)];
            } while (to.equals(from));
            Car car = new Car(from, to);
            lanes.get(from + car.getTurnDirection()).add(car);
        }
    }

    // 输出当前信号灯状态和车道情况
    public void printStatus() {
        System.out.println(lightControl.getTime() + "至" + (lightControl.getTime() + 5) + "秒：");
        for (String direction : DIRECTIONS) {
            System.out.println(direction + "：");
            String straightLight = lightControl.getCurrentLight(direction, "直行");
            String leftLight = lightControl.getCurrentLight(direction, "左转");
            System.out.println("信号灯情况：直行" + straightLight + "，左转" + leftLight + "，右转可通行");

            printLaneStatus(direction, "左转");
            printLaneStatus(direction, "直行");
            printLaneStatus(direction, "右转");
        }
        System.out.println("---------------------");
    }

    // 打印车道状态
    private void printLaneStatus(String direction, String turnDirection) {
        Queue<Car> lane = lanes.get(direction + turnDirection);
        System.out.print(turnDirection + "车道：");
        if (lane.isEmpty()) {
            System.out.println("没有来车");
        } else {
            int passingCars = 0;
            if (turnDirection.equals("右转") || lightControl.getCurrentLight(direction, turnDirection).equals("绿灯")) {
                passingCars = Math.min(5, lane.size());
            }
            System.out.println(passingCars + "辆车通过，" + (lane.size() - passingCars) + "辆车等待");
            for (int i = 0; i < passingCars; i++) {
                lane.poll();
            }
        }
    }

    // 模拟运行
    public void run() {
        while (true) {
            generateCars();
            printStatus();
            lightControl.updateLightState();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.run();
    }
}