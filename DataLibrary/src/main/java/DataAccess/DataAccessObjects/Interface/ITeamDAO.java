package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.*;

public interface ITeamDAO {
    boolean CreateTeam(TeamDTO team);
    TeamDTO GetTeam(int id);
    boolean addPlayer(int teamID, int playerID);
    boolean removePlayer(int teamID, int playerID);
    PlayerDTO[] getPlayers(int teamID);
    MatchDTO[] getMatches(int teamID);
}
