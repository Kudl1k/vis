package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.IMatchDAO;
import DataTransferObjects.MatchDTO;
import DataTransferObjects.TeamDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class MatchSqlDAO implements IMatchDAO {
    @Override
    public boolean CreateMatch(MatchDTO match) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "INSERT INTO matches (home_team_id, away_team_id, home_score, away_score, start_time, end_time, viewers, stadium, category_name, league_id, creator_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, match.getHomeTeam().getId());
                preparedStatement.setInt(2, match.getAwayTeam().getId());
                preparedStatement.setInt(3, match.getHomeScore());
                preparedStatement.setInt(4, match.getAwayScore());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp startTime = new Timestamp(dateFormat.parse(match.getStartTime()).getTime());
                preparedStatement.setTimestamp(5, startTime);
                preparedStatement.setTimestamp(6, null);
                preparedStatement.setInt(7, match.getViewers());
                preparedStatement.setString(8, match.getStadium());
                preparedStatement.setString(9, match.getCategory().getName());
                preparedStatement.setInt(10, match.getLeague().getId());
                preparedStatement.setInt(11, match.getCreator().getId());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public MatchDTO[] GetMatches() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM matches";
                java.sql.Statement statement = connection.createStatement();

                java.sql.ResultSet resultSet = statement.executeQuery(query);
                java.util.ArrayList<MatchDTO> matches = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    matches.add(new MatchDTO(
                            resultSet.getInt("id"),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("home_team_id")),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("away_team_id")),
                            resultSet.getInt("home_score"),
                            resultSet.getInt("away_score"),
                            resultSet.getString("start_time"),
                            resultSet.getString("end_time") != null ? resultSet.getString("end_time") : "",
                            resultSet.getInt("viewers"),
                            resultSet.getString("stadium"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name")),
                            GlobalConfig.connection.getLeagueDao().GetLeague(resultSet.getInt("league_id")),
                            GlobalConfig.connection.getUserDao().GetUser(resultSet.getInt("creator_id"))
                    ));
                }
                return matches.toArray(MatchDTO[]::new);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MatchDTO[] GetMatchesByLeague(int leagueID) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM matches WHERE league_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, leagueID);

                java.sql.ResultSet resultSet = preparedStatement.executeQuery();
                java.util.ArrayList<MatchDTO> matches = new java.util.ArrayList<>();
                while (resultSet.next()) {
                    matches.add(new MatchDTO(
                            resultSet.getInt("id"),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("home_team_id")),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("away_team_id")),
                            resultSet.getInt("home_score"),
                            resultSet.getInt("away_score"),
                            resultSet.getString("start_time"),
                            resultSet.getString("end_time"),
                            resultSet.getInt("viewers"),
                            resultSet.getString("stadium"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name")),
                            GlobalConfig.connection.getLeagueDao().GetLeague(resultSet.getInt("league_id")),
                            GlobalConfig.connection.getUserDao().GetUser(resultSet.getInt("creator_id"))
                    ));
                }
                return matches.toArray(MatchDTO[]::new);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MatchDTO GetMatch(int matchID) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM matches WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, matchID);

                java.sql.ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new MatchDTO(
                            resultSet.getInt("id"),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("home_team_id")),
                            GlobalConfig.connection.getTeamDao().GetTeam(resultSet.getInt("away_team_id")),
                            resultSet.getInt("home_score"),
                            resultSet.getInt("away_score"),
                            resultSet.getString("start_time"),
                            resultSet.getString("end_time"),
                            resultSet.getInt("viewers"),
                            resultSet.getString("stadium"),
                            GlobalConfig.connection.getCategoryDao().GetCategory(resultSet.getString("category_name")),
                            GlobalConfig.connection.getLeagueDao().GetLeague(resultSet.getInt("league_id")),
                            GlobalConfig.connection.getUserDao().GetUser(resultSet.getInt("creator_id"))
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean AddGoal(MatchDTO match, TeamDTO team) {
        try {
            if (team.getId() == match.getHomeTeam().getId()) {
                System.out.println("Home team");
                match.setHomeScore(match.getHomeScore() + 1);
            } else if (team.getId() == match.getAwayTeam().getId()) {
                System.out.println("Away team");
                match.setAwayScore(match.getAwayScore() + 1);
            } else {
                System.out.println("No team");
            }

            System.out.println(match.toString());

            String query = "UPDATE matches SET home_score = ?, away_score = ? WHERE id = ?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, match.getHomeScore());
            preparedStatement.setInt(2, match.getAwayScore());
            preparedStatement.setInt(3, match.getId());

            preparedStatement.executeUpdate();

            System.out.println("Goal added in sql");

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean RemoveGoal(MatchDTO match, TeamDTO team) {
        try {
            if (team.getId() == match.getHomeTeam().getId()) {
                match.setHomeScore(match.getHomeScore() - 1);
            } else if (team.getId() == match.getAwayTeam().getId()) {
                match.setAwayScore(match.getAwayScore() - 1);
            }

            String query = "UPDATE matches SET home_score = ?, away_score = ? WHERE id = ?";

            System.out.println(match.toString());

            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, match.getHomeScore());
            preparedStatement.setInt(2, match.getAwayScore());
            preparedStatement.setInt(3, match.getId());

            System.out.println("Goal removed");

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean ToggleFavorite(int matchID, int userID) {
        return false;
    }

    @Override
    public boolean EndMatch(MatchDTO match) {
        try {
            String query = "UPDATE matches SET end_time = ? WHERE id = ?";

            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, match.getEndTime());
            preparedStatement.setInt(2, match.getId());

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
