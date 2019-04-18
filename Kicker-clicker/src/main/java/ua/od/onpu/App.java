package ua.od.onpu;

import java.sql.*;
import java.util.*;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        ArrayList<Form> fList;
        ArrayList<Age_categories> ageList;
        Map<Integer, ArrayList<Categories>> mCateg = new TreeMap<Integer, ArrayList<Categories>>(); // <,должна быть какаято структура данных>
        // было бы не плохо sorted set
        String query_form = "Select Form.id, Form.disceplin, User.name,"
                + " User.gender, User.date_of_birth, date_of_comp, Form.weight, Form.fk_categor from Form inner join" +
                " User on fk_user = id_user" +
                " inner join Competitions comp on Form.fk_competit = comp.id";
        // + where fk_age_categ is null

        String age_query = "Select id, min_age, max_age from Age_categories";

        String queryUpdate = "Update Form set fk_age_categ = ?  where fk_age_categ IS NULL and Form.id =? ";

        String categor_query = "Select * from Categories order by id_age_cat, gender, Categories.wieght desc";// + будут в конце

        String categ_update = "update Form set fk_categor = ? where (id = ? and fk_categor is null) and (fk_age_categ <> 6)";

        DbWorker worker = new DbWorker();
        App app = new App();
        try {

            Statement statement = worker.getConnection().createStatement();

            Boolean isRetrieved = statement.execute(age_query);

            System.out.println("isRetrieved " + isRetrieved);
            ResultSet aRes = statement.executeQuery(age_query);
            ageList = app.insertAge_cat(aRes);
/*
            System.out.println("age table");
            for (Age_categories ag : ageList) {
                System.out.println(ag);
            }
            */
            //aRes.close();
            System.out.println("forma#1");
            ResultSet res = statement.executeQuery(query_form);
            fList = app.insertForm(res);

/*
            for (int i = 0; i < fList.size(); i++) {
                System.out.println("Age= " + fList.get(i).getAge() + " id= " + fList.get(i).getId());
            }
*/
// проверка работы метода
            System.out.println("======================");
            Form ff = new Form();
//            System.out.println(Arrays.toString(ff.determine_ageCateg(fList, ageList)));

// метод для заполнения fk_age_categ что бы было читаемо

            PreparedStatement prepSt = worker.getConnection().prepareStatement(queryUpdate);
            Box[] box = ff.determine_ageCateg(fList, ageList);
            for (int i = 0; i < box.length; i++) {
                Box x = box[i];
                prepSt.setInt(1, x.getId_ageCateg());
                prepSt.setInt(2, x.getId_form());
                System.out.println("Rows impacted: " + prepSt.executeUpdate());
            }
            //тут его конец
            //тут проверить добавились ли значения fk_age_categ в fList
            System.out.println("-+-+-+_+_+_+-+-+-+-+-=");
            System.out.println(fList);

            System.out.println("Test+++++++++");
            Categories c = new Categories();
            c.setWieght("+37");
            System.out.println(c.parsWieght());
            System.out.println("test22");
            String str = "+37";
            System.out.println(Integer.parseInt(str));
            char [] ch=str.toCharArray();
            System.out.println(ch[0]);

            //res.close();
            ResultSet res1 = statement.executeQuery(categor_query);


            //сделать метод
            while (res1.next()) {

                Categories cc = new Categories();
                ArrayList<Categories> al = new ArrayList<Categories>();
                cc.setId_age_cat(res1.getInt("id_age_cat"));
                cc.setId(res1.getInt("id"));
                cc.setWieght(res1.getString("wieght"));
                cc.setGender(res1.getString("gender"));
                al.add(cc);

                if (mCateg.containsKey(cc.getId_age_cat())) {
                    mCateg.get(cc.getId_age_cat()).add(cc);


                } else {
                    mCateg.put(cc.getId_age_cat(), new ArrayList<Categories>());
                    mCateg.get(cc.getId_age_cat()).add(cc);
                }
                //System.out.println(cc);// все нормально
            }
            System.out.println("cho po chem");
            System.out.println(mCateg.get(1));
            //  System.out.println(mCategor.get(1));
            System.out.println();


        } catch (SQLException e) {
            System.out.println("проблемассы");
            e.printStackTrace();
        }


    }


    public ArrayList<Form> insertForm(ResultSet res) throws SQLException {
        ArrayList<Form> lf = new ArrayList<Form>();
        while (res.next()) {
            Form f = new Form();
            f.setFk_user(new User());
            f.setFk_competit(new Competitions());
            f.setId(res.getInt("id"));
            f.setDisceplin(res.getString("disceplin"));
            f.getFk_user().setName(res.getString("name"));
            f.getFk_user().setGender(res.getString("gender"));
            f.getFk_user().setDate_of_birth(res.getString("date_of_birth"));
            f.getFk_competit().setDate_of_comp(res.getString("date_of_comp"));
            f.setWieght(res.getDouble("weight"));
            System.out.println(f);
            lf.add(f);
        }
        return lf;
    }

    public ArrayList<Age_categories> insertAge_cat(ResultSet res) throws SQLException {

        ArrayList<Age_categories> ageList = new ArrayList<Age_categories>();

        while (res.next()) {
            Age_categories ag = new Age_categories();
            ag.setId(res.getInt("id"));
            ag.setMin_age(res.getInt("min_age"));
            ag.setMax_age(res.getInt("max_age"));
            ageList.add(ag);
        }
        return ageList;
    }
}
