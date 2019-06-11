package ua.od.onpu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbWorker {

    private final String URL="jdbc:mysql://db4free.net:3306/kickers"
             +"?autoReconnect=true&useSSL=false"
            +"&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String USERNAME="vasildenov";  // vasildenov
    private final String PASSWORD ="Denov911";  // Denov911

    private Connection connection;

    public DbWorker(){
        try{
            connection=DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("соиденение установлено");
        }
        catch(SQLException ex){
            System.out.println("У НАС ПРОБЛЕМЫ");
        }
    }

    public Connection getConnection(){
        return connection;
    }


}
//"jdbc:mysql://localhost:3306/Kickers" +
//            "?autoReconnect=true&useSSL=false"  +
//            "&useLegacyDatetimeCode=false&serverTimezone=UTC";

//"jdbc:mysql://db4free.net:3306/kickers"
//        + "?autoReconnect=true&useSSL=false"
//        +"&useLegacyDatetimeCode=false&serverTimezone=UTC";