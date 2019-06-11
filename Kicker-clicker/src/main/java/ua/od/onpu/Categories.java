package ua.od.onpu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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

    public Categories man_max(ArrayList<Categories> categ) {
        Categories value=null;

        Iterator<Categories> it = categ.iterator();
        while (it.hasNext()) {
            Categories c = it.next();
            char[] ch = c.getWieght().toCharArray();
            if ( c.getGender().equals("M") && ch[0] == '+') {
                value = c;
                break;
            }
        }
         return value;

    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", id_age_cat=" + id_age_cat +
                        ", gender='" + gender + '\'' +
                        ", wieght='" + wieght;
    }


}

/*
public int man_max(ArrayList<Categories> categ) {
        int value =0;

        Iterator<Categories> it = categ.iterator();
        while (it.hasNext()) {
            Categories c = it.next();
            char[] ch = c.getWieght().toCharArray();
            if ( c.getGender().equals("M") && ch[0] == '+') {
                value = c.getId();
                break;
            }
        }
         return value;

    }
 */
