package ua.od.onpu;


import java.util.Arrays;
import java.util.*;

public class Test {
    String str1 = "2010-09-09";
    String str2 = "2019-04-01";
    int count = 0;

    public static void main(String[] args) {
        Test t = new Test();
        System.out.println(t.getAge(t.str1, t.str2));


       ArrayList<Integer>al = new ArrayList<Integer>(Arrays.asList(4,9));

        Iterator<Integer> it = al.iterator();
        while(it.hasNext()){
            Integer i = it.next();
            if(!it.hasNext()) System.out.println("zhopa");
            System.out.println(i);
        }

    }

    int getMax(BoxTest bt) {
        for (char s : bt.str.toCharArray()) {
            System.out.println(s);
        }
        char[] ch = bt.str.toCharArray();
        System.out.println(ch[0]);
        return 0;
    }

    public int getAge(String birthday, String competition) {
        int age = 0;

        String dateBd[] = new String[3];
        String dateComp[] = new String[3];
        Integer[] i1 = new Integer[3];
        Integer[] i2 = new Integer[3];
        for (String s : birthday.split("-")) {
            dateBd[count] = s;
            count++;
        }
        count = 0;
        for (String s : competition.split("-")) {
            dateComp[count] = s;
            count++;
        }
        // получил  массивы интов
        for (int i = 0; i < i1.length; i++) {
            i1[i] = Integer.parseInt(dateBd[i]);
            i2[i] = Integer.parseInt(dateComp[i]);
        }

        age = i2[0] - i1[0];
        if (i1[1] <= i2[1] && i1[2] <= i2[2]) age = age;
        else age = age - 1;


        return age;
    }

}

class BoxTest {
    String str="+89";
    int id = 9909;
}