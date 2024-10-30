package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.MatchDTO;
import DataTransferObjects.PlayerDTO;
import DataTransferObjects.TeamDTO;

public interface ITeamDAO {
    boolean CreateTeam(String name);
    TeamDTO GetTeam(int id);
    boolean addPlayer(int teamID, int playerID);
    boolean removePlayer(int teamID, int playerID);
    PlayerDTO[] getPlayers(int teamID);
    MatchDTO[] getMatches(int teamID);
}
