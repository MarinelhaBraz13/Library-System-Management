package com.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/library_management";

    private static final String USER = "root";

    private static final String PASSWORD = "123456";

    public static Connection getConnection() {

        Connection connection = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database connected successfully!");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return connection;
    }
}