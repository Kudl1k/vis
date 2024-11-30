package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.IPlayerDAO;
import DataTransferObjects.PlayerDTO;
import DataTransferObjects.PlayerHistoryDTO;
import DataTransferObjects.TeamDTO;

import java.sql.Connection;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class PlayerSqlDAO implements IPlayerDAO {
    @Override
    public PlayerDTO GetPlayer(int playerID) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM players WHERE id = ?";
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, playerID);

                java.sql.ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new PlayerDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("birth_date"),
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
    public boolean CreatePlayer(PlayerDTO player) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "INSERT INTO players (name, surname, birth_date, category_name) VALUES (?, ?, ?, ?)";
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, player.getName());
                preparedStatement.setString(2, player.getSurname());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Date date = Date.valueOf(player.getBirthDate());
                preparedStatement.setDate(3, date);
                preparedStatement.setString(4, player.getCategory().getName());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public PlayerDTO[] GetPlayers() {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM players";
                java.sql.Statement statement = connection.createStatement();

                java.sql.ResultSet resultSet = statement.executeQuery(query);
                java.util.ArrayList<PlayerDTO> players = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    players.add(new PlayerDTO(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("birth_date"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name"))
                    ));
                }
                return players.toArray(new PlayerDTO[0]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new PlayerDTO[0];
    }

    @Override
    public boolean UpdatePlayer(PlayerDTO player) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "UPDATE players SET name = ?, surname = ?, birth_date = ?, category_name = ? WHERE id = ?";
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, player.getName());
                preparedStatement.setString(2, player.getSurname());
                preparedStatement.setString(3, player.getBirthDate());
                preparedStatement.setString(4, player.getCategory().getName());
                preparedStatement.setInt(5, player.getId());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public PlayerHistoryDTO[] GetPlayerHistory(int playerID) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM player_history WHERE player_id = ?";
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, playerID);

                java.sql.ResultSet resultSet = preparedStatement.executeQuery();
                java.util.ArrayList<PlayerHistoryDTO> playerHistories = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    playerHistories.add(new PlayerHistoryDTO(
                            resultSet.getString("start_date"),
                            resultSet.getString("end_date"),
                            GetPlayer(resultSet.getInt("player_id")),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("team_id"))
                    ));
                }

                return playerHistories.toArray(new PlayerHistoryDTO[0]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new PlayerHistoryDTO[0];
    }

    @Override
    public PlayerDTO[] GetPlayersByTeam(TeamDTO team) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM player_history WHERE team_id = ?";
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, team.getId());

                java.sql.ResultSet resultSet = preparedStatement.executeQuery();
                java.util.ArrayList<PlayerDTO> players = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    players.add(GetPlayer(resultSet.getInt("player_id")));
                }

                return players.toArray(new PlayerDTO[0]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new PlayerDTO[0];
    }
}
