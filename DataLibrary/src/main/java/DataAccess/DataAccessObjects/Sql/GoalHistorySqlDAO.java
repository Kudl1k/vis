package DataAccess.DataAccessObjects.Sql;

import DataAccess.DataAccessObjects.Interface.IGoalHistoryDAO;
import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;

public class GoalHistorySqlDAO implements IGoalHistoryDAO {
    @Override
    public boolean CreateGoalHistory(GoalHistoryDTO goalHistory) {
        return false;
    }

    @Override
    public GoalHistoryDTO[] GetGoalHistories() {
        return new GoalHistoryDTO[0];
    }

    @Override
    public GoalHistoryDTO[] GetGoalHistories(MatchDTO match) {
        return new GoalHistoryDTO[0];
    }

    @Override
    public boolean DeleteGoalHistory(GoalHistoryDTO goalHistory) {
        return false;
    }
}
