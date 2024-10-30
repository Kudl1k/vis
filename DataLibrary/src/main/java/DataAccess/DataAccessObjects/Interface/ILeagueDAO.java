package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.LeagueDTO;
import DataTransferObjects.TeamDTO;

public interface ILeagueDAO {
    boolean CreateLeague(String name);
    LeagueDTO[] GetLeagues();
    LeagueDTO GetLeague(int id);
    TeamDTO[] GetTeams(int leagueID);
    boolean AddTeam(int leagueID, int teamID);
    boolean RemoveTeam(int leagueID, int teamID);
}
