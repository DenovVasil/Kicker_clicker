package ua.od.onpu;

public class Competitions {
    private int id;
    private String name;
    private String city;
    private String date_of_comp;

    Competitions(int id) {
        this.id = id;
    }
    Competitions(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getDate_of_comp() {
        return date_of_comp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate_of_comp(String date) {
        this.date_of_comp = date;
    }
}
