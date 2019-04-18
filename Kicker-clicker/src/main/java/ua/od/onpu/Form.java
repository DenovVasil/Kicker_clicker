package ua.od.onpu;

import java.sql.SQLOutput;
import java.util.*;


public class Form {

    private int id;
    private Competitions fk_competit;
    private double wieght;
    private String disceplin;
    private User fk_user;
    private Categories fk_categor;
    private int fk_age_categ;


    public int getFk_age_categ() {
        return fk_age_categ;
    }

    public void setFk_age_categ(int fk_age_categ) {
        this.fk_age_categ = fk_age_categ;
    }

    public Categories identifyAge() {

        fk_competit = new Competitions(1);
//        String [] strB=
        int[] dataB = new int[3];
        int[] dataC = new int[3];

        for (int i = 0; i < dataB.length; i++) {
            // fk_competit
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public Competitions getFk_competit() {
        return fk_competit;
    }

    public double getWieght() {
        return wieght;
    }

    public String getDisceplin() {
        return disceplin;
    }

    public User getFk_user() {
        return fk_user;
    }

    public Categories getFk_categor() {
        return fk_categor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFk_competit(Competitions fk_competit) {
        this.fk_competit = fk_competit;
    }

    public void setWieght(double wieght) {
        this.wieght = wieght;
    }

    public void setDisceplin(String disceplin) {
        this.disceplin = disceplin;
    }

    public void setFk_user(User fk_user) {
        this.fk_user = fk_user;
    }

    public void setFk_categor(Categories fk_categor) {
        this.fk_categor = fk_categor;
    }


    public int parsAge() {
        int age = 0;
        int count = 0;

        String dateBd[] = new String[3];
        String dateComp[] = new String[3];
        Integer[] i1 = new Integer[3];
        Integer[] i2 = new Integer[3];
        for (String s : fk_user.getDate_of_birth().split("-")) {
            dateBd[count] = s;
            count++;
        }
        count = 0;
        for (String s : fk_competit.getDate_of_comp().split("-")) {
            dateComp[count] = s;
            count++;
        }
        // получил  массивы интов
        for (int i = 0; i < i1.length; i++) {
            i1[i] = Integer.parseInt(dateBd[i]);
            i2[i] = Integer.parseInt(dateComp[i]);
        }

        age = i2[0] - i1[0];
        if (i1[1] <= i2[1] && i1[2] <= i2[2]) age = age; //?
        else age = age - 1;


        return age;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", fk_competit=" + fk_competit +
                ", wieght=" + wieght +
                ", disceplin='" + disceplin + '\'' +
                ", fk_user=" + fk_user +
                "fk_age_categ=" + fk_age_categ +
                ", fk_categor=" + fk_categor +
                '}';
    }

    /*
    в этом методе заполнить fk_age_categ
     */
    public Box[] determine_ageCateg(ArrayList<Form> lf, ArrayList<Age_categories> la) {

        ListIterator<Form> iter = lf.listIterator();
        Box[] box = new Box[lf.size()];
        int count = -1;
        while (iter.hasNext()) {
            Form f = iter.next();


            Box x = new Box();
            for (int i = 0; i < la.size(); i++) {

                if (f.parsAge() >= la.get(i).getMin_age() &&
                        f.parsAge() <= la.get(i).getMax_age()) {
                    x.setId_ageCateg(la.get(i).getId());
                    x.setId_form(f.getId());

                    box[++count] = x;

                    f.setFk_age_categ(la.get(i).getId());
                }

            }

        }

        return box;
    }


    //wieght categ     обосновать его
    //ArrayList<Form> тип
    public void determin_categ(ArrayList<Form> lf, Map<Integer, ArrayList<Categories>> mCat) {

        Iterator<Form> it = lf.iterator();
        Iterator<Categories> it2;
        while (it.hasNext()) {
            Form f = it.next();
            if (mCat.containsKey(f.getFk_age_categ())) {
                it2 = mCat.get(f.getFk_age_categ()).iterator();
                while(it2.hasNext()) {
                    Categories categ = it2.next();
                    if(categ.getGender().equals(f.getFk_user().getGender())){
                        if(f.getWieght()>=Integer.parseInt(categ.getWieght())){

                        }else{
                            // шаги итератора
                           // int max = categ.getId()
                        }


                    }
                    //categ.parsWieght()

                }

            }
           // else return null;
        }


        //return null;
    }


}


class Box {
    private int id_form;
    private int id_ageCateg;

    public void setId_form(int id_form) {
        this.id_form = id_form;
    }

    public void setId_ageCateg(int id_ageCateg) {
        this.id_ageCateg = id_ageCateg;
    }

    public int getId_form() {
        return id_form;
    }

    public int getId_ageCateg() {
        return id_ageCateg;
    }

    @Override
    public String toString() {
        return
                "id_form=" + id_form +
                        " id_ageCateg=" + id_ageCateg + ";";
    }
}
//            System.out.println(f.getId()+" "+ f.fk_user.getName()+" "
//                    + f.fk_competit.getDate_of_comp() + " " + f.fk_user.getDate_of_birth()+
//                    " возраст " + f.getAge());