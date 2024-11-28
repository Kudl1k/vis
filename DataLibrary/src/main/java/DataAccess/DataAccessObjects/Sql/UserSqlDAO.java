package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.IUserDAO;
import DataTransferObjects.UserDTO;

import java.sql.*;

public class UserSqlDAO implements IUserDAO {

    @Override
    public boolean CreateUser(String name, String surname, String email, String password, String role) {
        Connection conn = null;
        boolean isValid = true;
        try {
            String connectionString = GlobalConfig.CnnString(GlobalConfig.dbName);
            conn = DriverManager.getConnection(connectionString);
            if (conn != null){
                String sql = "INSERT INTO User(name, surname, email, password, role, last_log, created_at)\n"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, email);
                statement.setString(4, password);
                statement.setString(5, role);
                Timestamp cur_timestamp = new Timestamp(System.currentTimeMillis());
                statement.setString(6, cur_timestamp.toString());
                statement.setString(7, cur_timestamp.toString());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            isValid = false;
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return isValid;
    }

    @Override
    public UserDTO LoginUser(String email, String password) {
        return null;
    }

    @Override
    public UserDTO GetUser(int userID) {
        return null;
    }

    @Override
    public boolean UpdateUser(UserDTO user) {
        return false;
    }
}
