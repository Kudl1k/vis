package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.ITeamDAO;
import DataTransferObjects.CategoryDTO;
import DataTransferObjects.TeamDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class TeamSqlDAO implements ITeamDAO {
    @Override
    public boolean CreateTeam(TeamDTO team) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "INSERT INTO teams (name, country_code, league_id, category_name) VALUES (?,?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, team.getName());
                preparedStatement.setString(2, team.getCountryCode());
                preparedStatement.setInt(3, team.getLeague().getId());
                preparedStatement.setString(4, team.getCategory().getName());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public TeamDTO GetTeam(int id) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM teams WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new TeamDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("country_code"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name")),
                            GlobalConfig.connection.getLeagueDao().GetLeague(resultSet.getInt("league_id"))
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public TeamDTO[] GetTeamsByLeague(int leagueID) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM teams WHERE league_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, leagueID);

                ResultSet resultSet = preparedStatement.executeQuery();

                TeamDTO[] teams = new TeamDTO[0];

                while (resultSet.next()) {
                    TeamDTO[] newTeams = new TeamDTO[teams.length + 1];
                    System.arraycopy(teams, 0, newTeams, 0, teams.length);
                    newTeams[teams.length] = new TeamDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("country_code"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name")),
                            GlobalConfig.connection.getLeagueDao().GetLeague(resultSet.getInt("league_id"))
                    );
                    teams = newTeams;
                }
                return teams;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public TeamDTO[] GetTeamsByCategory(CategoryDTO category) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM teams WHERE category_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, category.getName());

                ResultSet resultSet = preparedStatement.executeQuery();

                TeamDTO[] teams = new TeamDTO[0];

                while (resultSet.next()) {
                    TeamDTO[] newTeams = new TeamDTO[teams.length + 1];
                    System.arraycopy(teams, 0, newTeams, 0, teams.length);
                    newTeams[teams.length] = new TeamDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("country_code"),
                            category,
                            GlobalConfig.connection.getLeagueDao().GetLeague(resultSet.getInt("league_id"))
                    );
                    teams = newTeams;
                }
                return teams;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
