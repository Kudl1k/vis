package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;

public interface IMatchDAO {
    boolean CreateMatch(MatchDTO match);
    MatchDTO[] GetMatches();
    MatchDTO[] GetMatchesByLeague(int leagueID);
    MatchDTO GetMatch(int matchID);
    boolean AddGoal(GoalHistoryDTO goal);
    boolean ToggleFavorite(int matchID, int userID);
}
