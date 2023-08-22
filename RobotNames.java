import java.util.Random;

public enum RobotNames {
    R2D2, ThreePiO, Macron, Voltron, Wallie, Eva, Bender, Ava, Terminator, Cyrax;
    public static String getRobotName() {
        return String.valueOf(RobotNames.values()[new Random().nextInt(RobotNames.values().length)]);
    }
}
