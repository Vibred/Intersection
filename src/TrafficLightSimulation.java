import java.util.*;
import java.util.concurrent.*;

public class TrafficLightSimulation {
    // 定义四个方向
    private static final String[] DIRECTIONS = {"南向", "北向", "东向", "西向"};

    // 定义信号灯状态
    private enum LightState {
        S_N_STRAIGHT, // 南北直行绿灯
        S_N_LEFT,     // 南北左转绿灯
        E_W_STRAIGHT, // 东西直行绿灯
        E_W_LEFT      // 东西左转绿灯
    }

    // 车辆类，包含来向和去向信息
    static class Car {
        String fromDirection;
        String toDirection;

        Car(String from, String to) {
            this.fromDirection = from;
            this.toDirection = to;
        }

        boolean isTurningRight() {
            return getTurnDirection().equals("右转");
        }

        String getTurnDirection() {
            switch (fromDirection) {
                case "南向":
                    return toDirection.equals("西向") ? "左转" : (toDirection.equals("东向") ? "右转" : "直行");
                case "北向":
                    return toDirection.equals("东向") ? "左转" : (toDirection.equals("西向") ? "右转" : "直行");
                case "东向":
                    return toDirection.equals("南向") ? "左转" : (toDirection.equals("北向") ? "右转" : "直行");
                case "西向":
                    return toDirection.equals("北向") ? "左转" : (toDirection.equals("南向") ? "右转" : "直行");
                default:
                    return "未知";
            }
        }
    }

    // 交通灯控制类
    static class TrafficLightControl {
        private LightState currentState = LightState.S_N_STRAIGHT;
        private int time = 0;

        // 返回当前信号灯状态
        String getCurrentLight(String direction, String turnDirection) {
            switch (currentState) {
                case S_N_STRAIGHT:
                    if (direction.equals("南向") || direction.equals("北向")) {
                        if (turnDirection.equals("直行")) {
                            return "绿灯";
                        } else {
                            return "红灯";
                        }
                    }
                    return "红灯";
                case S_N_LEFT:
                    if (direction.equals("南向") || direction.equals("北向")) {
                        if (turnDirection.equals("左转")) {
                            return "绿灯";
                        } else {
                            return "红灯";
                        }
                    }
                    return "红灯";
                case E_W_STRAIGHT:
                    if (direction.equals("东向") || direction.equals("西向")) {
                        if (turnDirection.equals("直行")) {
                            return "绿灯";
                        } else {
                            return "红灯";
                        }
                    }
                    return "红灯";
                case E_W_LEFT:
                    if (direction.equals("东向") || direction.equals("西向")) {
                        if (turnDirection.equals("左转")) {
                            return "绿灯";
                        } else {
                            return "红灯";
                        }
                    }
                    return "红灯";
                default:
                    return "红灯";
            }
        }

        // 更新信号灯状态
        void updateLightState() {
            switch (currentState) {
                case S_N_STRAIGHT:
                    currentState = LightState.S_N_LEFT;
                    break;
                case S_N_LEFT:
                    currentState = LightState.E_W_STRAIGHT;
                    break;
                case E_W_STRAIGHT:
                    currentState = LightState.E_W_LEFT;
                    break;
                case E_W_LEFT:
                    currentState = LightState.S_N_STRAIGHT;
                    break;
            }
            time += 5;
        }

        int getTime() {
            return time;
        }

        LightState getCurrentState() {
            return currentState;
        }
    }

    // 模拟器类
    static class Simulator {
        private Map<String, Queue<Car>> lanes = new HashMap<>();
        private TrafficLightControl lightControl = new TrafficLightControl();
        private Random random = new Random();

        Simulator() {
            for (String direction : DIRECTIONS) {
                lanes.put(direction + "左转", new LinkedList<>());
                lanes.put(direction + "直行", new LinkedList<>());
                lanes.put(direction + "右转", new LinkedList<>());
            }
        }

        // 随机生成来车并放入车道队列
        void generateCars() {
            int carCount = random.nextInt(10) + 1;
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
        void printStatus() {
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
        void run() {
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
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.run();
    }
}
