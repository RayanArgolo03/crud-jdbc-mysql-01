package database;

import exceptions.DbConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class DbConnection {
    private static Connection connection;

    private DbConnection() {
    }

    public static Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                Properties properties = loadProperties();

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            }

        } catch (ClassNotFoundException e) {
            throw new DbConnectionException("MySQL Drive not found!");
        } catch (SQLException e) {
            throw new DbConnectionException(e.getMessage());
        }

        return connection;
    }

    private static Properties loadProperties() {

        try (FileInputStream inputStream = new FileInputStream("jdbc.properties")) {

            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;

        } catch (IOException e) {
            throw new DbConnectionException("Error! " + e.getMessage());
        }

    }
}
