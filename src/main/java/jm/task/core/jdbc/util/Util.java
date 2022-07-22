package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String dbHost = "localHost";
    private static final String dbPort = "3306";
    private static final String dbUser = "root";
    private static final String dbPass = "12345";
    private static final String dbName = "katatraining";

    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        return DriverManager.getConnection(connectionString, dbUser, dbPass);
    }
}
