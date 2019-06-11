package ua.od.onpu;

import java.util.*;

public class Fights {

    private int id;
    private int tatami_numb;
    private Competitions comp;
    private User id_userR;
    private User id_userB;
    private String score;
    private String lap;
    private int winer;
    private String disceplin;
    private Categories fk_categor;
    private Age_categories age_categories;

    public void setId(int id) {
        this.id = id;
    }

    public void setAge_categories(Age_categories age_categories) {
        this.age_categories = age_categories;
    }

    public void setTatami_numb(int tatami_numb) {
        this.tatami_numb = tatami_numb;
    }

    public void setComp(Competitions id_comp) {
        this.comp = id_comp;
    }

    public void setId_userR(User id_userR) {
        this.id_userR = id_userR;
    }

    public void setId_userB(User id_userB) {
        this.id_userB = id_userB;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setLap(String lap) {
        this.lap = lap;
    }

    public void setWiner(int winer) {
        this.winer = winer;
    }

    public void setDisceplin(String disceplin) {
        this.disceplin = disceplin;
    }

    public void setFk_categor(Categories fk_categor) {
        this.fk_categor = fk_categor;
    }

    public int getId() {
        return id;
    }

    public int getTatami_numb() {
        return tatami_numb;
    }

    public Competitions getComp() {
        return comp;
    }

    public User getId_userR() {
        return id_userR;
    }

    public User getId_userB() {
        return id_userB;
    }

    public String getScore() {
        return score;
    }

    public String getLap() {
        return lap;
    }

    public int getWiner() {
        return winer;
    }

    public Age_categories getAge_categories() { return age_categories; }

    public String getDisceplin() {
        return disceplin;
    }

    public Categories getFk_categor() {
        return fk_categor;
    }


    @Override
    public String toString() {
        return "id = " + id +
                " id_userR " + id_userR.getSurname() + " " + id_userR.getName() +

                " id_userB " + id_userB.getSurname() + " " + id_userB.getName() +
                "lap " + lap;
    }


    public String beauti_output(String s) {                 // будем подавать ключ map

        ArrayList<Character> al = new ArrayList<>();
        for (Character ch : s.toCharArray()) al.add((ch));

        if (lap.equals("1/1")) lap = "final";
        char ch1 = al.get(al.size() - 2);
        char ch2 = al.get(al.size() - 1);


        return " <tr>\n" +
                "<td><p>" + "#" + id + " " + lap + " " + "2x2</p>\n" +
                "<p>" + ch1+ch2 + " " + fk_categor.getId()+" " + age_categories.getName()+    //хорошая идея fk_categor.getName()
                 " "+fk_categor.getWieght()+" "+fk_categor.getGender() +" </p>\n" +
                "</td>\n" +
                "<td><p>"+id_userR.getSurname() + " " + id_userR.getName() +"</p>\n" +
                "<p>" +id_userR.getClub()+ "</p></td>\n" +
                "<td><p>" +id_userB.getSurname() + " " + id_userB.getName() +"</p>\n" +
                "<p>"+id_userB.getClub()+"</p></td>\n" +
                "</tr>\n";
    }
}
