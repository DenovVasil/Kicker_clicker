package ua.od.onpu;

public class Fights {

    private int id;
    private int tatami_numb;
    private int id_comp;
    private int id_userR;
    private int id_userB;
    private String score;
    private String lap;
    private String winer;

    public int getId() {
        return id;
    }

    public int getTatami_numb() {
        return tatami_numb;
    }

    public int getId_comp() {
        return id_comp;
    }

    public int getId_userR() {
        return id_userR;
    }

    public int getId_userB() {
        return id_userB;
    }

    public String getScore() {
        return score;
    }

    public String getLap() {
        return lap;
    }

    public String getWiner() {
        return winer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTatami_numb(int tatami_numb) {
        this.tatami_numb = tatami_numb;
    }

    public void setId_comp(int id_comp) {
        this.id_comp = id_comp;
    }

    public void setId_userR(int id_userR) {
        this.id_userR = id_userR;
    }

    public void setId_userB(int id_userB) {
        this.id_userB = id_userB;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setLap(String lap) {
        this.lap = lap;
    }

    public void setWiner(String winer) {
        this.winer = winer;
    }
}
