package dk.ecosolutions.oms.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static String url;
    private static String databasename;
    private static String username;
    private static String password;

    static {
        Properties prop = new Properties();
        String fileUrl = "config.properties";
        try {
            InputStream input = Database.class.getClassLoader().getResourceAsStream(fileUrl);
            prop.load(input);
            url = "jdbc:sqlserver://" + prop.getProperty("db.url", "1433");
            databasename = prop.getProperty("db.databasename");
            username = prop.getProperty("db.username", "sa");
            password = prop.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            return DriverManager.getConnection(url + ";databaseName=" + databasename, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database");
        }
    }
}
