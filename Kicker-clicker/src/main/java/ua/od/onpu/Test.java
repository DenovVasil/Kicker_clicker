package ua.od.onpu;


import java.util.Arrays;

public class Test {
    String str1 = "2010-09-09";
    String str2 = "2019-04-01";
    int count = 0;

    public static void main(String[] args) {
        Test t = new Test();
        System.out.println(t.getAge(t.str1, t.str2));
    }

    public int getAge(String birthday, String competition) {
        int age = 0;

        String dateBd[] = new String[3];
        String dateComp[] = new String[3];
        Integer[] i1=new Integer[3];
        Integer[] i2=new Integer[3];
        for (String s : birthday.split("-")) {
            dateBd[count] = s;
            count++;
        }
        count=0;
        for (String s : competition.split("-")) {
            dateComp[count] = s;
            count++;
        }
        // получил  массивы интов
        for (int i = 0; i <i1.length ; i++) {
            i1[i]=Integer.parseInt(dateBd[i]);
            i2[i]=Integer.parseInt(dateComp[i]);
        }

        age=i2[0]-i1[0];
        if(i1[1] <=i2[1] && i1[2]<=i2[2]) age=age;
        else age=age-1;


        return age;
    }

}
