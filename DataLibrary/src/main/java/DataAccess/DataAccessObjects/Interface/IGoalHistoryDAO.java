package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;

public interface IGoalHistoryDAO {
    boolean CreateGoalHistory(GoalHistoryDTO goalHistory);
    GoalHistoryDTO[] GetGoalHistories();
    GoalHistoryDTO[] GetGoalHistories(MatchDTO match);
    boolean DeleteGoalHistory(GoalHistoryDTO goalHistory);
}
