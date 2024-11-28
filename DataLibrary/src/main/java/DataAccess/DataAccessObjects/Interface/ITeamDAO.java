package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.*;

public interface ITeamDAO {
    boolean CreateTeam(TeamDTO team);
    TeamDTO GetTeam(int id);
    TeamDTO[] GetTeamsByLeague(int leagueID);
    TeamDTO[] GetTeamsByCategory(CategoryDTO category);
}
