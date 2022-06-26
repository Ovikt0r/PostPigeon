package com.oviktor.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DbConnection {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DB_DRIVER);
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                log.info("Connection is successful!");
            } catch (ClassNotFoundException e) {
                log.error("DB driver {} wasn't found: ", DB_DRIVER, e);
            } catch (SQLException e) {
                log.error("Connection error: {}", e.getMessage(), e);
            }
        }
        return connection;
    }

}
