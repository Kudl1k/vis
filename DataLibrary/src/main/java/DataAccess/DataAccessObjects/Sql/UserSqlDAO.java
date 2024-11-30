package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.IUserDAO;
import DataTransferObjects.UserDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class UserSqlDAO implements IUserDAO {


    @Override
    public boolean CreateUser(String name, String surname, String email, String password, String role) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "INSERT INTO users (name, surname, email, password, role) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, role);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserDTO LoginUser(String email, String password) {
        try(Connection connection = getConnection()) {
            if (connection != null) {


                String query = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);


                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    query = "UPDATE users SET last_log = ? WHERE email = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    preparedStatement.setString(2, email);
                    preparedStatement.executeUpdate();



                    return new UserDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role"),
                            resultSet.getString("created_at"),
                            resultSet.getString("last_log")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDTO GetUser(int userID) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM users WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userID);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new UserDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("role"),
                            resultSet.getString("created_at"),
                            resultSet.getString("last_log")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean UpdateUser(UserDTO user) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "UPDATE users SET name = ?, surname = ?, email = ?, password = ?, role = ?, updated_at = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getRole());
                preparedStatement.setString(6, user.getLast_log());
                preparedStatement.setInt(7, user.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
