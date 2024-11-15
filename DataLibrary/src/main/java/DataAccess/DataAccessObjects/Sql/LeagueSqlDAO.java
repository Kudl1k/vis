package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.ILeagueDAO;
import DataTransferObjects.LeagueDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class LeagueSqlDAO implements ILeagueDAO {

    @Override
    public boolean CreateLeague(LeagueDTO league) {
        boolean isValid = false;
        Connection conn = null;
        try {
            String connectionString = GlobalConfig.CnnString(GlobalConfig.dbName);
            conn = DriverManager.getConnection(connectionString);
            if (conn != null) {
                String sql = "INSERT INTO League(name, countryCode)\n"
                        + "VALUES(?, ?);";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, league.getName());
                statement.setString(2, league.getCountryCode());
                statement.executeUpdate();
                isValid = true;
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
        return isValid;
    }

    @Override
    public LeagueDTO[] GetLeagues() {
        return new LeagueDTO[0];
    }

    @Override
    public LeagueDTO[] GetLeaguesByCategory(String category) {
        return new LeagueDTO[0];
    }

    @Override
    public LeagueDTO[] GetLeaguesByCountry(String countryCode) {
        return new LeagueDTO[0];
    }


    @Override
    public boolean AddTeam(LeagueDTO league, int teamID) {
        return false;
    }

    @Override
    public boolean RemoveTeam(LeagueDTO league, int teamID) {
        return false;
    }
}
