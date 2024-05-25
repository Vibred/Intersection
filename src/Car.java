public class Car {
    String fromDirection;
    String toDirection;

    public Car(String from, String to) {
        this.fromDirection = from;
        this.toDirection = to;
    }

    boolean isTurningRight() {
        return getTurnDirection().equals("右转");
    }  // 暂时没有使用的方法

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