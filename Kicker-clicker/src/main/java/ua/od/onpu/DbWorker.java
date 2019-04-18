package ua.od.onpu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbWorker {

    private final String URL="jdbc:mysql://localhost:3306/Kickers" +
            "?autoReconnect=true&useSSL=false"  +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String USERNAME="root";
    private final String PASSWORD ="root";

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
