// TrafficLight.java
public class TrafficLight implements Runnable {
    private String direction;
    private boolean isGreen;

    public TrafficLight(String direction) {
        this.direction = direction;
        this.isGreen = false;
    }

    // 切换信号灯状态
    public void toggleLight() {
        isGreen = !isGreen;
        System.out.println(direction + "信号灯切换为" + (isGreen ? "绿灯" : "红灯"));
    }

    // 返回当前信号灯状态
    public boolean isGreen() {
        return isGreen;
    }

    @Override
    public void run() {
        while (true) {
            toggleLight();
            try {
                Thread.sleep(10000); // 每10秒切换一次信号灯
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
