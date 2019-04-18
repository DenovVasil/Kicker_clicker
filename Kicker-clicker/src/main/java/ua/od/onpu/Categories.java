package ua.od.onpu;

public class Categories extends Age_categories {

    private int id;
    private int id_age_cat;
    private String gender;
    private String wieght;

    @Override
    public int getId() {
        return id;
    }

    public int getId_age_cat() {
        return id_age_cat;
    }

    public String getGender() {
        return gender;
    }

    public String getWieght() {
        return wieght;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setId_age_cat(int id_age_cat) {
        this.id_age_cat = id_age_cat;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setWieght(String wieght) {
        this.wieght = wieght;
    }

    public int parsWieght() {
        Integer i = 0;
        for (String str : wieght.split("-")) {
            if (!str.equals("")) {
                i = Integer.parseInt(str);
            }
        }
        return i;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", id_age_cat=" + id_age_cat +
                ", gender='" + gender + '\'' +
                ", wieght='" + wieght ;
    }



}
