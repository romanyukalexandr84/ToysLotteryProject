public class Toy {
    private int id;
    private String type;
    private String name;
    private double weight;

    public Toy(int id, String type, String name, double weight) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "id:" + id + " || type:" + type + " || name:" + name + " || frequency:" + (int)(weight*100) + "%";
    }
}