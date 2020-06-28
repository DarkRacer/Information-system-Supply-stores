package Database;

import java.sql.*;

public class Connect {
    private static String URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&serverTimezone=UTC";
    private static String USERNAME = "root";
    private static String PASSWORD = "0000";
    Connection connection;
    public Connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
