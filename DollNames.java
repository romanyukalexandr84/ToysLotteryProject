import java.util.Random;

public enum DollNames {
    Masha, Dasha, Barbie, Sindie, Milana, Herda, Anya, Petrushka, Wendy, Christina;
    public static String getDollName() {
        return String.valueOf(DollNames.values()[new Random().nextInt(DollNames.values().length)]);
    }
}
