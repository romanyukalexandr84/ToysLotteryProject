import java.util.Random;

public enum ConstructorNames {
    Lego, Bauer, Builder, Technic, SmartKid, Junior, Blocks, Minecraft, Railway, Cubes;
    public static String getConsName() {
        return String.valueOf(ConstructorNames.values()[new Random().nextInt(ConstructorNames.values().length)]);
    }
}
