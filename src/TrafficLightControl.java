public class TrafficLightControl {
    private enum LightState {
        S_N_STRAIGHT, // 南北直行绿灯
        S_N_LEFT,     // 南北左转绿灯
        E_W_STRAIGHT, // 东西直行绿灯
        E_W_LEFT      // 东西左转绿灯
    }

    private LightState currentState = LightState.S_N_STRAIGHT;
    private int time = 0;

    // 返回当前信号灯状态
    public String getCurrentLight(String direction, String turnDirection) {
        //
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
    public void updateLightState() {
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

    public int getTime() {
        return time;
    }

    public LightState getCurrentState() {
        return currentState;
    }
}