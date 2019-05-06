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
        // было бы не плохо tree set
        String query_form = "Select Form.id, Form.disceplin, User.name,"
                + " User.gender, User.date_of_birth, date_of_comp, Form.weight, Form.fk_categor from Form inner join" +
                " User on fk_user = id_user" +
                " inner join Competitions comp on Form.fk_competit = comp.id";
        // + where fk_age_categ is null

        String age_query = "Select id, min_age, max_age from Age_categories";

        String queryUpdate = "Update Form set fk_age_categ = ?  where fk_age_categ IS NULL and Form.id =? ";

        String categor_query = "Select * from Categories order by id_age_cat, gender, Categories.wieght desc";// + будут в конце

        String categ_update = "Update Form set fk_categor = ? where Form.id = ? " ;//and (fk_age_categ <> 6)";

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
            for (Form f : fList) {

                System.out.println("id= " + f.getId() +
                        " name " + f.getFk_user().getName() +
                        " wieght=" + f.getWieght() +
                        " disceplin= " + f.getDisceplin() +
                        " date_of_comp= " + f.getFk_competit().getDate_of_comp() +
                        " date_of_birth= " + f.getFk_user().getDate_of_birth() +
                        " gender= " + f.getFk_user().getGender());
            }

/*
            for (int i = 0; i < fList.size(); i++) {
                System.out.println("Age= " + fList.get(i).getAge() + " id= " + fList.get(i).getId());
            }
*/
// проверка работы метода
            System.out.println("======================");


            Form ff = new Form();
            ff.determine_ageCateg(fList, ageList);
            System.out.println("forma#2");
            for (Form f : fList) {


                System.out.println("id= " + f.getId() +
                        " name= " + f.getFk_user().getName() +
                        " wieght= " + f.getWieght() +
                        " disceplin= " + f.getDisceplin() +
                        " date_of_comp= " + f.getFk_competit().getDate_of_comp() +
                        " date_of_birth=" + f.getFk_user().getDate_of_birth() +
                        " fk_age_categ=" + f.getFk_age_categ().getId());
            }
            //update db
            PreparedStatement prepSt = worker.getConnection().prepareStatement(queryUpdate);
            for (Form f : fList) {
                prepSt.setInt(1, f.getFk_age_categ().getId());
                prepSt.setInt(2, f.getId());
                System.out.println("Rows impacted: " + prepSt.executeUpdate());
            }


            ResultSet res1 = statement.executeQuery(categor_query);

            mCateg = app.inserCateg(res1);


            ff.determin_categ(fList, mCateg);
            //"update Form set fk_categor = ? where (id = ? and fk_categor is null)"
            PreparedStatement prepSt2 =worker.getConnection().prepareStatement(categ_update);
            for (Form f: fList){
                prepSt2.setInt(1,f.getFk_categor().getId());
                prepSt2.setInt(2,f.getId());
                System.out.println("Rows impacted: " + prepSt2.executeUpdate());
            }
            System.out.println("Test determin_categ");
            for (Form f : fList) {


                System.out.println("id= " + f.getId() +
                        " name " + f.getFk_user().getName() +
                        " wieght=" + f.getWieght() +
                        " disceplin= " + f.getDisceplin() +
                        " date_of_comp= " + f.getFk_competit().getDate_of_comp() +
                        " date_of_birth=" + f.getFk_user().getDate_of_birth() +
                        " fk_age_categ=" + f.getFk_age_categ().getId() +
                        " gender= " + f.getFk_user().getGender() +
                        " categ= " + f.getFk_categor().getId());

            }




        } catch (SQLException e) {
            System.out.println("проблемассы");
            e.printStackTrace();
        }


    }


    public Map<Integer, ArrayList<Categories>> inserCateg(ResultSet res) throws SQLException {

        Map<Integer, ArrayList<Categories>> mCateg =
                new TreeMap<Integer, ArrayList<Categories>>();

        while (res.next()) {

            Categories cc = new Categories();
            ArrayList<Categories> al = new ArrayList<Categories>();
            cc.setId_age_cat(res.getInt("id_age_cat"));
            cc.setId(res.getInt("id"));
            cc.setWieght(res.getString("wieght"));
            cc.setGender(res.getString("gender"));
            al.add(cc);

            if (mCateg.containsKey(cc.getId_age_cat())) {
                mCateg.get(cc.getId_age_cat()).add(cc);

            } else {
                mCateg.put(cc.getId_age_cat(), new ArrayList<Categories>());
                mCateg.get(cc.getId_age_cat()).add(cc);
            }

        }
        return mCateg;
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
