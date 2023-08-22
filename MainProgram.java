import java.io.*;
import java.util.*;

public class MainProgram {
    //Задаем список игрушек в лототроне
    static ArrayList<Toy> prizes = new ArrayList<>();

    //Метод для взятия игрушки из лототрона
    public static Toy getToy() {
        return prizes.remove(new Random().nextInt(prizes.size()));
    }

    //Метод проверки, является ли переданное значение целым числом
    public static boolean isNumeric(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Метод задания удельного веса каждого типа игрушки
    public static double toyWeight(String type) {
        System.out.println("Введите удельный вес (дробное число от 0 до 1) игрушек типа " + type);
        double weight = 0;
        boolean weightIsCorrect = false;
        while (!weightIsCorrect) {
            try {
                Scanner scanner = new Scanner(System.in);
                weight = scanner.nextDouble();
                if (weight < 0 || weight > 1) {
                    throw new RuntimeException();
                }
                weightIsCorrect = true;
            } catch (Exception e) {
                System.out.println("Данные некорректны, повторите ввод");
            }
        }
        return weight;
    }

    //Метод чтения файла
    public static List<String> readFile(File file) {
        List<String> lst = new ArrayList<>();
        try (FileReader fr = new FileReader(file); BufferedReader bf = new BufferedReader(fr)) {
            String line;
            while ((line = bf.readLine()) != null) {
                lst.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + file.getName());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lst;
    }

    //Метод записи данных в файл
    public static void writeFile(List<String> lst, File file, Toy toy, int prizesInLottery) {
        try (FileWriter fw = new FileWriter(file); BufferedWriter bf = new BufferedWriter(fw)) {
            for (String item : lst) {
                bf.write(item);
                bf.newLine();
            }
            bf.write(toy.toString());
            bf.newLine();
            bf.write("Prizes to be awarded: " + prizesInLottery);
            bf.newLine();
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + file.getName());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        //Задаем количество призов
        Scanner scan = new Scanner(System.in);
        String totalPrizes;
        do {
            System.out.println("Введите общее количество игрушек в розыгрыше (в числовом формате):");
            totalPrizes = scan.nextLine();
        } while (!isNumeric(totalPrizes));

        //Задаем удельные веса каждого типа игрушек, проверяем чтобы их сумма была равна 1 или 0,99
        boolean totalWeightIsCorrect;
        double constructorWeight = 0;
        double robotWeight = 0;
        double dollWeight = 0;
        do {
            totalWeightIsCorrect = true;
            try {
                constructorWeight = toyWeight(String.valueOf(ToyTypes.values()[0]));
                robotWeight = toyWeight(String.valueOf(ToyTypes.values()[1]));
                dollWeight = toyWeight(String.valueOf(ToyTypes.values()[2]));
                if ((constructorWeight + robotWeight + dollWeight) != 1 &&
                        (int)((constructorWeight + robotWeight + dollWeight)*100) != 99){
                    throw new RuntimeException();
                }
            } catch (RuntimeException e) {
                System.out.println("Сумма удельных весов не равна единице, повторите ввод");
                totalWeightIsCorrect = false;
            }
        } while (!totalWeightIsCorrect);

        //Подсчитываем количество каждого типа игрушек в розыгрыше, несущественную разницу
        //в результате округлений дозаполняем конструкторами
        int numberOfConstructors = (int) (Integer.parseInt(totalPrizes) * constructorWeight);
        int numberOfRobots = (int) (Integer.parseInt(totalPrizes) * robotWeight);
        int numberOfDolls = (int) (Integer.parseInt(totalPrizes) * dollWeight);
        while ((numberOfConstructors + numberOfRobots + numberOfDolls) != Integer.parseInt(totalPrizes)) {
            numberOfConstructors++;
        }

        //Заполняем лототрон игрушками в случайном порядке
        int count = 0;
        do {
            int newPrize = new Random().nextInt(3);
            switch (newPrize) {
                case 0 -> {
                    if (numberOfConstructors != 0) {
                        prizes.add(new Toy(count, String.valueOf(ToyTypes.values()[0]), ConstructorNames.getConsName(), constructorWeight));
                        numberOfConstructors--;
                        count++;
                    }
                }
                case 1 -> {
                    if (numberOfRobots != 0) {
                        prizes.add(new Toy(count, String.valueOf(ToyTypes.values()[1]), RobotNames.getRobotName(), robotWeight));
                        numberOfRobots--;
                        count++;
                    }
                }
                default -> {
                    if (numberOfDolls != 0) {
                        prizes.add(new Toy(count, String.valueOf(ToyTypes.values()[2]), DollNames.getDollName(), dollWeight));
                        numberOfDolls--;
                        count++;
                    }
                }
            }
        } while (count < Integer.parseInt(totalPrizes));

        //Вращаем лототрон и достаем приз, записываем в файл с перечнем призов к выдаче
        while (!prizes.isEmpty()){
            Collections.shuffle(prizes);
            File file = new File("prizes.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            List<String> fileContent = readFile(file);
            writeFile(fileContent, file, getToy(), prizes.size());
        }
    }
}
