package ua.od.onpu;

public class User {

    private int id_user;
    private String name;
    private String surname;
    private String club;
    private String gender;
    private String date_of_birth;

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public int getId() {
        return id_user;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getClub() {
        return club;
    }

    public String getGender() {
        return gender;
    }

    public void setId(int id) {
        this.id_user = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setGender(String gendor) {
        this.gender = gender;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id_user +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", club='" + club + '\'' +
                ", gendor='" + gender + '\'' +
                ", date_of_birth='" + date_of_birth + '\'' +
                '}';
    }
}
