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
                    f.getFk_age_categ().setName(age.getName());
                    break;

                }
            }//else return null; // хорошо бы выбросить exception

        }

    }


    //wieght categ     обосновать его
    //ArrayList<Form> тип
    public void determine_categ(ArrayList<Form> lf, Map<Integer, ArrayList<Categories>> mCateg) {
        ArrayList<Categories> al = new ArrayList<Categories>();
        Iterator<Form> it_f = lf.iterator();
        Iterator<Categories> it_c;
        while (it_f.hasNext()) {
            Form f = it_f.next();
            if (mCateg.containsKey(f.getFk_age_categ().getId())) {   // не оч надо,или можно бросить illigual arg ex
                it_c = mCateg.get(f.getFk_age_categ().getId()).iterator(); // итератор для нужной колекции (по возросту)
               // f.setFk_categor(new Categories());
                al.clear();
                while (it_c.hasNext()) {
                    Categories c = it_c.next();
                    // условие веса равности к категории
                    if (c.getGender().equals(f.getFk_user().getGender()) &&
                            f.getWieght() == c.parsWieght()) {
                        f.setFk_categor(c);
//                        f.getFk_categor().setId(c.getId());
//                        f.getFk_categor().setWieght(c.getWieght());
                        break;
                    }
                    if (c.getGender().equals(f.getFk_user().getGender()) &&
                            f.getWieght() < c.parsWieght()) {
                        al.add(c);
                        f.setFk_categor(min(al));
                        //f.getFk_categor().setId(min(al));//


                    }
                    if (c.getGender().equals(f.getFk_user().getGender()) && // возврат абсолюта только для W
                            (!it_c.hasNext() && al.size() == 0)) {
                        f.setFk_categor(c);
                    } else if (f.getFk_user().getGender().equals("M") &&
                            (it_c.hasNext() == false && al.size() == 0)) {
                        f.setFk_categor(c.man_max(mCateg.get(f.getFk_age_categ().getId())));
                    }
                }
            }

        }
    }


    // даже не тестил с пом sublist

    public Categories min(ArrayList<Categories> categ) { // кажется тут одно лишнее сравнение
        int min_wieght = categ.get(0).parsWieght();
        Categories min_ob = categ.get(0);

        Iterator<Categories> it = categ.iterator();
       // Categories c = it.next();
        while (it.hasNext()) {
           Categories  c = it.next();
            if (c.parsWieght() < min_wieght) {
                min_wieght = c.parsWieght();
                min_ob = c;
            }
        }

        return min_ob;
    }


    public Map<String, ArrayList<Form>> sort_by_categ(ArrayList<Form> fList) {

        Map<String, ArrayList<Form>> sortForm = new HashMap<>();
        String str;


        Iterator<Form> it = fList.iterator();

        while (it.hasNext()) {
            Form f = it.next();
            str = "" + f.getFk_categor().getId() + f.getDisceplin();

            if (sortForm.containsKey(str)) {
                sortForm.get(str).add(f);
            } else {
                sortForm.put(str, new ArrayList<>());
                sortForm.get(str).add(f);
            }

        }

        return sortForm;
    }

    //имя, фамилия, раздел, возраст, весовая
    public void show_singl(ArrayList<Age_categories> lAge,
                           Map<Integer, ArrayList<Categories>> mCat) {
        String aCateg_name = "";
        String categ = "";
        for (Age_categories age : lAge) {
            if (age.getId() == fk_age_categ.getId()) {
                aCateg_name = age.getName();
            }
        }

        Collection<Integer> col = mCat.keySet();
        Iterator<Integer> it = col.iterator();

        while (it.hasNext()) {
            Integer a = it.next();
            if (fk_age_categ.getId() == a)
                for (Categories c : mCat.get(a)) {
                    if (getFk_categor().getId() == c.getId()) {
                        categ = c.getWieght();
                    }
                }
        }
        System.out.println(getFk_user().getName() + " " +
                getFk_user().getSurname() + " " +
                disceplin + " " +     //f.getDisceplin()+ " "
                aCateg_name + " " +    //  getFk_age_categ().getId()
                categ);
    }


}
/*
    public void determine_categ(ArrayList<Form> lf, Map<Integer, ArrayList<Categories>> mCateg) {
        ArrayList<Categories> al = new ArrayList<Categories>();
        Iterator<Form> it_f = lf.iterator();
        Iterator<Categories> it_c;
        while (it_f.hasNext()) {
            Form f = it_f.next();
            if (mCateg.containsKey(f.getFk_age_categ().getId())) {   // не оч надо,или можно бросить illigual arg ex
                it_c = mCateg.get(f.getFk_age_categ().getId()).iterator(); // итератор для нужной колекции (по возросту)
                f.setFk_categor(new Categories());
                al.clear();
                while (it_c.hasNext()) {
                    Categories c = it_c.next();
                    // условие веса равности к категории
                    if (c.getGender().equals(f.getFk_user().getGender()) &&
                            f.getWieght() == c.parsWieght()) {
                        f.getFk_categor().setId(c.getId());
                        f.getFk_categor().setWieght(c.getWieght());
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
                    } else if (f.getFk_user().getGender().equals("M") &&
                            (it_c.hasNext() == false && al.size() == 0)) {
                        f.getFk_categor().setId(c.man_max(mCateg.get(f.getFk_age_categ().getId())));
                    }
                }
            }

        }
    }
 */

 /*
    public int min(ArrayList<Categories> categ) { // кажется тут одно лишнее сравнение
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
    */


