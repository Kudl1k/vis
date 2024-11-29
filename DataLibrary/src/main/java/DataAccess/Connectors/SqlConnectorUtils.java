package DataAccess.Connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnectorUtils {
    private static final String connectiongString = GlobalConfig.CnnString("db.connectionString");
    private static final String user = GlobalConfig.CnnString("user");
    private static final String password = GlobalConfig.CnnString("password");

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(connectiongString, user, password);
            if (connection == null) {
                throw new SQLException("Connection is null");
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
