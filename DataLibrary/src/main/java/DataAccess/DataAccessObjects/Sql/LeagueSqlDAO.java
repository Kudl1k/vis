package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.ILeagueDAO;
import DataTransferObjects.CategoryDTO;
import DataTransferObjects.LeagueDTO;

import java.sql.*;
import java.util.ArrayList;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class LeagueSqlDAO implements ILeagueDAO {

    @Override
    public boolean CreateLeague(LeagueDTO league) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "INSERT INTO leagues (name, country_code, category_name) VALUES (?,?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, league.getName());
                preparedStatement.setString(2, league.getCountryCode());
                preparedStatement.setString(3, league.getCategoryDTO().getName());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public LeagueDTO[] GetLeagues() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM leagues";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);
                ArrayList<LeagueDTO> leagues = new ArrayList<>();
                while (resultSet.next()) {
                    leagues.add(new LeagueDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("country_code"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name"))
                    ));
                }
                return leagues.toArray(LeagueDTO[]::new);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public LeagueDTO GetLeague(int id) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM leagues WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new LeagueDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("country_code"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name"))
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public LeagueDTO[] GetLeaguesByCategory(CategoryDTO category) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM leagues WHERE category_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, category.getName());

                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<LeagueDTO> leagues = new ArrayList<>();
                while (resultSet.next()) {
                    leagues.add(new LeagueDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("country_code"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name"))
                    ));
                }
                return leagues.toArray(LeagueDTO[]::new);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public LeagueDTO[] GetLeaguesByCountry(String countryCode) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM leagues WHERE country_code = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, countryCode);

                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<LeagueDTO> leagues = new ArrayList<>();
                while (resultSet.next()) {
                    leagues.add(new LeagueDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("country_code"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name"))
                    ));
                }
                return leagues.toArray(LeagueDTO[]::new);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public boolean AddTeam(LeagueDTO league, int teamID) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "UPDATE teams SET league_id = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, league.getId());
                preparedStatement.setInt(2, teamID);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean RemoveTeam(LeagueDTO league, int teamID) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "UPDATE teams SET league_id = NULL WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, teamID);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
