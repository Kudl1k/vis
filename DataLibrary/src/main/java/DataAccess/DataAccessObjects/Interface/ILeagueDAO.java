package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.LeagueDTO;
import DataTransferObjects.TeamDTO;

public interface ILeagueDAO {
    boolean CreateLeague(LeagueDTO league);
    LeagueDTO[] GetLeagues();
    LeagueDTO[] GetLeaguesByCountry(String countryCode);
    TeamDTO[] GetTeams(LeagueDTO league);
    boolean AddTeam(LeagueDTO league, int teamID);
    boolean RemoveTeam(LeagueDTO league, int teamID);
}
