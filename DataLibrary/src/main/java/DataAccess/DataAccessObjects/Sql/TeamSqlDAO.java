package DataAccess.DataAccessObjects.Sql;

import DataAccess.DataAccessObjects.Interface.ITeamDAO;
import DataTransferObjects.CategoryDTO;
import DataTransferObjects.TeamDTO;

import java.sql.Connection;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class TeamSqlDAO implements ITeamDAO {
    @Override
    public boolean CreateTeam(TeamDTO team) {
        try(Connection connection = getConnection()) {
            if (connection != null) {

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public TeamDTO GetTeam(int id) {
        return null;
    }

    @Override
    public TeamDTO[] GetTeamsByLeague(int leagueID) {
        return new TeamDTO[0];
    }

    @Override
    public TeamDTO[] GetTeamsByCategory(CategoryDTO category) {
        return new TeamDTO[0];
    }
}
