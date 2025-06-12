package com.example.hms;

import java.sql.Connection;
import java.sql.DriverManager;

import static java.lang.Class.forName;

public class DB_Connection {

    public static Connection connDB() {
        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospital_management_system",
                    "root",
                    "Batman@2025sql");
            return connect;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
