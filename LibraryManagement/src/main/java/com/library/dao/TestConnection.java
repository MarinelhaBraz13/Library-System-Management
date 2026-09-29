package com.library.dao;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("SUCCESS: Database connected!");
        } else {
            System.out.println("FAILED: Database connection failed!");
        }
    }
}