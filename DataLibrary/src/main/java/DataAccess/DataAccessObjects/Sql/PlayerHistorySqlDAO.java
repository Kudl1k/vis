package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.IPlayerHistoryDAO;
import DataTransferObjects.PlayerDTO;
import DataTransferObjects.PlayerHistoryDTO;
import DataTransferObjects.TeamDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class PlayerHistorySqlDAO implements IPlayerHistoryDAO {
    @Override
    public boolean addPlayerHistory(PlayerHistoryDTO playerHistory) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "INSERT INTO player_history (player_id, team_id, start_date, end_date) VALUES (?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, playerHistory.getPlayer().getId());
                preparedStatement.setInt(2, playerHistory.getTeam().getId());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                Date startDate = Date.valueOf(playerHistory.getStartDate());
                Date endDate = Date.valueOf(playerHistory.getEndDate());

                preparedStatement.setDate(3, startDate);
                preparedStatement.setDate(4, endDate);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean removeLastPlayerHistory(PlayerDTO player) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "DELETE FROM player_history WHERE player_id = ? ORDER BY start_date DESC LIMIT 1";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, player.getId());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public PlayerHistoryDTO[] getPlayerHistory(PlayerDTO player) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM player_history WHERE player_id = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, player.getId());

                java.sql.ResultSet resultSet = preparedStatement.executeQuery();
                java.util.ArrayList<PlayerHistoryDTO> playerHistories = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    playerHistories.add(new PlayerHistoryDTO(
                            resultSet.getString("start_date"),
                            resultSet.getString("end_date"),
                            player,
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("team_id"))
                    ));
                }

                return playerHistories.toArray(new PlayerHistoryDTO[0]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public PlayerHistoryDTO[] getPlayersHistoryInTeam(TeamDTO team) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM player_history WHERE team_id = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, team.getId());

                java.sql.ResultSet resultSet = preparedStatement.executeQuery();
                java.util.ArrayList<PlayerHistoryDTO> playerHistories = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    playerHistories.add(new PlayerHistoryDTO(
                            resultSet.getString("start_date"),
                            resultSet.getString("end_date"),
                            GlobalConfig.connection.getPlayerDao().GetPlayer(resultSet.getInt("player_id")),
                            team
                    ));
                }

                return playerHistories.toArray(new PlayerHistoryDTO[0]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
