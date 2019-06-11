package ua.od.onpu;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;


/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        ArrayList<Form> fList;
        ArrayList<Age_categories> ageList;
        Map<Integer, ArrayList<Categories>> mCateg;

        Map<String, ArrayList<Form>> sortF;
        Map<String, ArrayList<Fights>> mapFights;

        ArrayList<Form> singl = new ArrayList<>();

        String query_form = "Select Form.id, Form.disceplin, User.name,"
                + " User.gender, User.surname, User.date_of_birth,  User.club, date_of_comp,fk_competit, Form.weight, Form.fk_categor from Form inner join" +
                " User on fk_user = id_user" +
                " inner join Competitions comp on Form.fk_competit = comp.id";
        // + where fk_age_categ is nul>

        String age_query = "Select id, min_age, max_age, name from Age_categories";

        String queryUpdate = "Update Form set fk_age_categ = ?  where fk_age_categ IS NULL and Form.id =? ";

        String categor_query = "Select * from Categories order by id_age_cat, gender, Categories.wieght desc";// + будут в конце

        String categ_update = "Update Form set fk_categor = ? where Form.id = ? ";//and (fk_age_categ <> 6)";



        String s1="<html>\n" +
                " <head>\n" +
                "  <title>списоки пар</title>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"GENERATOR\" content=\"Microsoft FrontPage 4.0\">\n" +
                "  <meta name=\"ProgId\" content=\"FrontPage.Editor.Document\">\n" +
                "  <link rel=\"stylesheet\" href=\"style.css\"\n" +
                " </head>\n"+
                "<body>\n"+
" <h1>Название турнира</h1>\n"+

  "<table class=\"topics\" border=\"1\" width=\"100%\" cellpadding=\"5\">\n"+
               "<tr>\n"+
  	"<th width=\"200\"><p>пара (этап)ф-ла боя</p>\n"+
  	      "<p>   код раздела   </p>\n"+
  	      "<th>Красный угл</th>\n"+
  	      "<th>Синий угл</th>\n"+
  	"</tr>\n";


       String s2="";
       String s3 ="</table>\n" +
               "</body>\n" +
               "</html>";
        DbWorker worker = new DbWorker();
        App app = new App();
        try {

            Statement statement = worker.getConnection().createStatement();

            Boolean isRetrieved = statement.execute(age_query);

            System.out.println("isRetrieved " + isRetrieved);
            ResultSet aRes = statement.executeQuery(age_query);
            ageList = app.insertAge_cat(aRes);


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


            ff.determine_categ(fList, mCateg);
            //"update Form set fk_categor = ? where (id = ? and fk_categor is null)"
            PreparedStatement prepSt2 = worker.getConnection().prepareStatement(categ_update);
//            for (Form f : fList) {
//                prepSt2.setInt(1, f.getFk_categor().getId());
//                prepSt2.setInt(2, f.getId());
//                System.out.println("Rows impacted: " + prepSt2.executeUpdate());
//            }
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
                        " categ= " + f.getFk_categor().getId()+
                        " wieght= "+ f.getFk_categor().getWieght());
                
            }

            System.out.println("+++++++======map_test");
            sortF = ff.sort_by_categ(fList);
            System.out.println("одиночки");
            singl = app.determ_singls(sortF);
            for (Form f : singl) f.show_singl(ageList, mCateg);
//сделать какие-то изменения в базе или еще где-то
            //если они еще остались закинуть их в тPlaces
            //потом удалить из sortF
            System.out.println("Сортировка");
            System.out.println(sortF);

            System.out.println("Сетка");
            mapFights = app.generate_grid(sortF);
            for(String s: mapFights.keySet()){
               for(Fights f: mapFights.get(s)){
                   System.out.println(s);
                   System.out.println(f);
               }
            }
            System.out.println("Test####");


            for(String s: mapFights.keySet()){
                for(Fights fight : mapFights.get(s)){
                   // System.out.println(fight.beauti_output(s));
                    s2+=fight.beauti_output(s);
                }
            }

            s1=s1+s2+s3;
            System.out.println(s1);
            app.generate_table(s1);

//            System.out.println(sortF);


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
            f.getFk_user().setSurname(res.getString("surname"));
            f.getFk_competit().setId(res.getInt("id"));
            f.getFk_user().setClub(res.getString("club"));
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
            ag.setName((res.getString("name")));
            ageList.add(ag);
        }
        return ageList;
    }

    public ArrayList<Form> determ_singls(Map<String, ArrayList<Form>> sortF) {
        ArrayList<Form> singl = new ArrayList<>();
        Set<String> keys = sortF.keySet();
        for (String s : keys) {
            if (sortF.get(s).size() == 1) {
                singl.add(sortF.get(s).get(0));
            }
        }
        return singl;
    }

// допустим в нашей sortF НЕТ singl
    //

    public Map<String, ArrayList<Fights>> generate_grid(Map<String, ArrayList<Form>> sortF) {

        HashMap<String, ArrayList<Fights>> fights = new HashMap<>();               // подумай над типом
        String lap = "";
        double part = 0;          //тоже самое чо и lap
        double lucky = 0, unlucky = 0;
        int countF;           //count of fighters
        int id = 0;


        Set<String> keys = sortF.keySet();
        for (String s : keys) {
            if (sortF.get(s).size() > 1) {
                countF = sortF.get(s).size();
                for (int i = 1; i < 6; i++) {
                    part = Math.pow(2, i);
                    if (countF <= part) {
                        lucky = part - countF;
                        unlucky = countF - lucky;
                        break;
                    }
                }
                fights.put(s, new ArrayList<Fights>());

                Iterator<Form> it = sortF.get(s).listIterator();

                while (it.hasNext()) {
                    Form form = it.next();
                    Fights fight = new Fights();

                    while (unlucky != 0) {              //if||while            // unlucky - всегда парное
                        id++;
                        fight.setId(id);
                        fight.setComp(form.getFk_competit());
                        fight.setId_userR(form.getFk_user());
                        fight.setFk_categor(form.getFk_categor());
                        fight.setAge_categories(form.getFk_age_categ());

                        unlucky--;
                        form = it.next();                              // обработка ошибок
                        fight.setId_userB(form.getFk_user());
                        fight.setLap("1/" + (int) part/2);
                        fights.get(s).add(fight);
                        --unlucky;
                    }
                    while (lucky > 0) {   // думаю While надо
                        fight=new Fights();    // ууууууууууу
                        form = it.next();
                        id++;
                        fight.setId(id);// это новый файт
                        fight.setComp(form.getFk_competit()); // надо проверить
                        fight.setId_userR(form.getFk_user());
                        fight.setFk_categor(form.getFk_categor());
                        fight.setAge_categories(form.getFk_age_categ());
                       // lucky--;// стрeмно
                        int temporary =(int)lucky;

                        if (--temporary != 0) {
                            form = it.next();
                            fight.setId_userB(form.getFk_user());
                            fight.setLap("1/" + part / 2);
                            fights.get(s).add(fight);
                            lucky=lucky- 2; // блин
                        } else {
                            fight.setId_userB(new User());
                            fight.getId_userB().setSurname("Победитель пары");
                            fight.getId_userB().setName(" " + (id-1));
                            fight.setLap("1/" + (int) part / 4); // проверить надо
                            fights.get(s).add(fight);
                            --lucky;
                        }
                    }
                }

            }
        }
        return fights;
    }


    public void generate_table(String s) {
        String str="/home/denov/Рабочий стол/tabl/index.html";
        try (FileWriter writer = new FileWriter(str, false)) {

            writer.write(s);
            // запись по символам
            writer.append('\n');
           // writer.append('E');

            writer.flush();


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}