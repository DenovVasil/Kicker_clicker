package ua.od.onpu;

public class Age_categories {
    private int id;
    private String name;
    private int min_age;
    private int max_age;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMin_age() {
        return min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                " name='" + name  +
                " min_age=" + min_age +
                " max_age=" + max_age ;
    }
}
