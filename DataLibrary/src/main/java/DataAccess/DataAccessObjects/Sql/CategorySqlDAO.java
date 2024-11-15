package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.ICategoryDAO;
import DataTransferObjects.CategoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorySqlDAO implements ICategoryDAO {
    @Override
    public boolean CreateCategory(CategoryDTO category) {
        Connection conn = null;
        boolean isValid = true;
        try {
            String connectionString = GlobalConfig.CnnString(GlobalConfig.dbName);
            conn = DriverManager.getConnection(connectionString);
            if (conn != null) {
                String sql = "INSERT INTO Category(name)\n"
                        + "VALUES(?);";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, category.getName());
                statement.executeUpdate();
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
    public CategoryDTO[] GetCategories() {
        Connection conn = null;
        List<CategoryDTO> categories = new ArrayList<>();
        try {
            String connectionString = GlobalConfig.CnnString(GlobalConfig.dbName);
            conn = DriverManager.getConnection(connectionString);
            if (conn != null) {
                String sql = "SELECT id, name\n"
                        + "FROM Category";
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while(rs.next())
                {
                    CategoryDTO category = new CategoryDTO(
                        rs.getString("name")
                    );
                    categories.add(category);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return categories.toArray(new CategoryDTO[0]);
    }

    @Override
    public CategoryDTO GetCategory(String name) {
        Connection conn = null;
        CategoryDTO category = null;
        try {
            String connectionString = GlobalConfig.CnnString(GlobalConfig.dbName);
            conn = DriverManager.getConnection(connectionString);
            if (conn != null) {
                String sql = "SELECT name\n"
                        + "FROM Category\n"
                        + "WHERE name = ?;";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, name);
                ResultSet rs = statement.executeQuery();
                if (rs.next())
                {
                    category = new CategoryDTO(
                        rs.getString("name")
                    );
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return category;
    }
}
