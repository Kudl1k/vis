package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.CategoryDTO;
import DataTransferObjects.LeagueDTO;

public interface ILeagueDAO {
    boolean CreateLeague(LeagueDTO league);
    LeagueDTO[] GetLeagues();
    LeagueDTO GetLeague(int id);
    LeagueDTO[] GetLeaguesByCategory(CategoryDTO category);
    LeagueDTO[] GetLeaguesByCountry(String countryCode);
    boolean AddTeam(LeagueDTO league, int teamID);
    boolean RemoveTeam(LeagueDTO league, int teamID);
}
