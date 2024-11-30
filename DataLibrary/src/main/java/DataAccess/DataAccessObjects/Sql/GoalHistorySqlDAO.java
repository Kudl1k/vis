package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.IGoalHistoryDAO;
import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;

import java.sql.Connection;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class GoalHistorySqlDAO implements IGoalHistoryDAO {
    @Override
    public boolean CreateGoalHistory(GoalHistoryDTO goalHistory) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "INSERT INTO goal_history (minute, player_id, team_id, match_id, creator_id) VALUES (?,?, ?, ?, ?)";
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, goalHistory.getMinute());
                preparedStatement.setInt(2, goalHistory.getPlayer().getId());
                preparedStatement.setInt(3, goalHistory.getTeam().getId());
                preparedStatement.setInt(4, goalHistory.getMatch().getId());
                preparedStatement.setInt(5, goalHistory.getCreator().getId());

                if (GlobalConfig.connection.getMatchDao().AddGoal(goalHistory.getMatch(), goalHistory.getTeam())) {
                    System.out.println("Goal added to match");
                } else {
                    return false;
                }
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public GoalHistoryDTO[] GetGoalHistories() {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM goal_history";
                java.sql.Statement statement = connection.createStatement();

                java.sql.ResultSet resultSet = statement.executeQuery(query);
                java.util.ArrayList<GoalHistoryDTO> goalHistories = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    goalHistories.add(new GoalHistoryDTO(
                            resultSet.getInt("minute"),
                            GlobalConfig.connection.getPlayerDao().GetPlayer(resultSet.getInt("player_id")),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("team_id")),
                            GlobalConfig.connection.getMatchDao().GetMatch(resultSet.getInt("match_id")),
                            GlobalConfig.connection.getUserDao().GetUser(resultSet.getInt("creator_id"))
                    ));
                }
                return goalHistories.toArray(GoalHistoryDTO[]::new);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public GoalHistoryDTO[] GetGoalHistories(MatchDTO match) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM goal_history WHERE match_id = ?";
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, match.getId());

                java.sql.ResultSet resultSet = preparedStatement.executeQuery();
                java.util.ArrayList<GoalHistoryDTO> goalHistories = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    goalHistories.add(new GoalHistoryDTO(
                            resultSet.getInt("minute"),
                            GlobalConfig.connection.getPlayerDao().GetPlayer(resultSet.getInt("player_id")),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("team_id")),
                            GlobalConfig.connection.getMatchDao().GetMatch(resultSet.getInt("match_id")),
                            GlobalConfig.connection.getUserDao().GetUser(resultSet.getInt("creator_id"))
                    ));
                }
                return goalHistories.toArray(GoalHistoryDTO[]::new);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean DeleteGoalHistory(GoalHistoryDTO goalHistory) {
        try(Connection connection = getConnection()) {
            if (connection != null) {
                String query = "DELETE FROM goal_history WHERE minute = ? AND player_id = ? AND team_id = ? AND match_id = ? AND creator_id = ?";
                java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, goalHistory.getMinute());
                preparedStatement.setInt(2, goalHistory.getPlayer().getId());
                preparedStatement.setInt(3, goalHistory.getTeam().getId());
                preparedStatement.setInt(4, goalHistory.getMatch().getId());
                preparedStatement.setInt(5, goalHistory.getCreator().getId());

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
