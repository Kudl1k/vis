package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;
import DataTransferObjects.TeamDTO;

public interface IMatchDAO {
    boolean CreateMatch(MatchDTO match);
    MatchDTO[] GetMatches();
    MatchDTO[] GetMatchesByLeague(int leagueID);
    MatchDTO GetMatch(int matchID);
    boolean AddGoal(MatchDTO match, TeamDTO team);
    boolean RemoveGoal(MatchDTO match, TeamDTO team);
    boolean ToggleFavorite(int matchID, int userID);
    boolean EndMatch(MatchDTO match);
}
