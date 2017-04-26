package sample;


import java.sql.*;

public class DBconnector {
    private Connection connection;
    private Statement state;

    DBconnector() {

        try {
            Class.forName("com.mysql.jdbc.Driver");//Set driver
            this.connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11168779", "sql11168779", "E9Jd9Jc9Pa");
            this.state = connection.createStatement();
            System.out.println("connected to DB");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }


    public Statement getState() {
        return state;
    }




}


