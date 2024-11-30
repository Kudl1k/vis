package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.ICategoryDAO;
import DataTransferObjects.CategoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class CategorySqlDAO implements ICategoryDAO {
    @Override
    public boolean CreateCategory(CategoryDTO category) {
        try(Connection connection = getConnection()){
            if (connection != null) {
                String sql = "INSERT INTO Category (name) VALUES (?);";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, category.getName());
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public CategoryDTO[] GetCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        try(Connection connection = getConnection()){
            if (connection != null) {
                String sql = "SELECT name FROM Categories;";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    categories.add(new CategoryDTO(
                            rs.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories.toArray(new CategoryDTO[0]);
    }

    @Override
    public CategoryDTO GetCategory(String name) {
        try(Connection connection = getConnection()){
            if (connection != null) {
                String sql = "SELECT name FROM Categories WHERE name = ?;";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                ResultSet rs = statement.executeQuery();



                if (rs.next()) {
                    CategoryDTO category = new CategoryDTO(
                            rs.getString("name")
                    );
                    return category;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
