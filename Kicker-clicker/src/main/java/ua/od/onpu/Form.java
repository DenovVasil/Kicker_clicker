package ua.od.onpu;


import java.util.*;


public class Form {

    private int id;
    private Competitions fk_competit;
    private double wieght;
    private String disceplin;
    private User fk_user;
    private Categories fk_categor;
    private Age_categories fk_age_categ;


    public Age_categories getFk_age_categ() {
        return fk_age_categ;
    }

    public void setFk_age_categ(Age_categories fk_age_categ) {
        this.fk_age_categ = fk_age_categ;
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
        if (i1[1] <= i2[1] && i1[2] <= i2[2]) age = age; //? (!)
        else age = age - 1;


        return age;
    }

    @Override
    public String toString() {
        return
                "id= " + id;
//                +" name "+getFk_user().getName()+
//                " wieght=" + wieght +
//                " disceplin=" + disceplin  +
//                " date_of_comp= " + fk_competit.getDate_of_comp() +
//                " date_of_birth=" + getFk_user().getDate_of_birth() +
//                " fk_age_categ=" + fk_age_categ.getId() +
//                " fk_categor.id=" + fk_categor.getId();
    }

    /*
    в этом методе заполнить fk_age_categ
     */
    public void determine_ageCateg(ArrayList<Form> lf, ArrayList<Age_categories> la) {

        ListIterator<Form> iter = lf.listIterator();


        while (iter.hasNext()) {
            Form f = iter.next();
            Iterator<Age_categories> age_it = la.iterator();
            while (age_it.hasNext()) {
                Age_categories age = age_it.next();
                if (f.parsAge() >= age.getMin_age() &&
                        f.parsAge() <= age.getMax_age()) {
                    f.setFk_age_categ(new Age_categories());
                    f.getFk_age_categ().setId(age.getId());
                    break;

                }
            }//else return null; // хорошо бы выбросить exception

        }

    }


    //wieght categ     обосновать его
    //ArrayList<Form> тип
    public void determin_categ(ArrayList<Form> lf, Map<Integer, ArrayList<Categories>> mCateg) {
        ArrayList<Categories> al = new ArrayList<Categories>();
        Iterator<Form> it_f = lf.iterator();
        Iterator<Categories> it_c;
        while (it_f.hasNext()) {
            Form f = it_f.next();
            if (mCateg.containsKey(f.getFk_age_categ().getId())) {   // не оч надо,или можно бросить illigual arg ex
                it_c = mCateg.get(f.getFk_age_categ().getId()).iterator(); // итератор для нужной колекции (по возросту)
                f.setFk_categor(new Categories());  //это хорошо, или тут проблема
                al.clear();
                while (it_c.hasNext()) {
                    Categories c = it_c.next();
                    // условие веса равности к категории
                    if (c.getGender().equals(f.getFk_user().getGender()) &&
                            f.getWieght() == c.parsWieght()) {
                        f.getFk_categor().setId(c.getId());
                        break;
                    }
                    if (c.getGender().equals(f.getFk_user().getGender()) &&
                            f.getWieght() < c.parsWieght()) {
                        al.add(c);
                        f.getFk_categor().setId(min(al));

                    }
                    if (c.getGender().equals(f.getFk_user().getGender()) && // возврат абсолюта только для W
                            (it_c.hasNext() == false && al.size() == 0)) {
                        f.getFk_categor().setId(c.getId());
                    }else if(f.getFk_user().getGender().equals("M") &&
                            (it_c.hasNext() == false && al.size() == 0)){
                        f.getFk_categor().setId(c.man_max(mCateg.get(f.getFk_age_categ().getId())));
                    }
                }
            }

        }
    }


    // даже не тестил с пом sublist
    public int min(ArrayList<Categories> categ) {
        int min_wieght = categ.get(0).parsWieght();
        int min_id = categ.get(0).getId();
        Iterator<Categories> it = categ.iterator();
        while (it.hasNext()) {
            Categories c = it.next();
            if (c.parsWieght() < min_wieght) {
                min_wieght = c.parsWieght();
                min_id = c.getId();
            }
        }

        return min_id;
    }

}
//return null;



