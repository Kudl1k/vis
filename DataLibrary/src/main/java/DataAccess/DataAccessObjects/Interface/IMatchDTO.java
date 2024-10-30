package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;

public interface IMatchDTO {
    boolean CreateMatch(MatchDTO match);
    MatchDTO GetMatch(int matchID);
    boolean AddGoal(GoalHistoryDTO goal);
    boolean ToggleFavorite(int matchID, int userID);
}
