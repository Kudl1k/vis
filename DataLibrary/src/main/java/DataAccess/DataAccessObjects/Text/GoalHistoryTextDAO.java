package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.IGoalHistoryDAO;
import DataAccess.DataAccessObjects.Mappers.GoalHistoryTextDataMapper;
import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GoalHistoryTextDAO implements IGoalHistoryDAO {

    public static String goalHistoryFile = "GoalHistory.csv";

    private GoalHistoryTextDataMapper goalHistoryMapper;

    public GoalHistoryTextDAO() {
        goalHistoryMapper = new GoalHistoryTextDataMapper();
    }

    @Override
    public boolean CreateGoalHistory(GoalHistoryDTO goalHistory) {
        ArrayList<GoalHistoryDTO> allGoalHistories = new ArrayList<>(List.of(GetGoalHistories()));
        ArrayList<GoalHistoryDTO> matchGoalHistories = new ArrayList<>(List.of(GetGoalHistories(goalHistory.getMatch())));

        //check if the minute is not behind the last goal

        System.out.println("Match goal histories: " + goalHistory.toString());

        if (!matchGoalHistories.isEmpty()) {
            GoalHistoryDTO lastGoalHistory = matchGoalHistories.get(matchGoalHistories.size() - 1);
            if (lastGoalHistory.getMinute() > goalHistory.getMinute()) {
                return false;
            }
        }

        allGoalHistories.add(goalHistory);

        if (!GlobalConfig.connection.getMatchDao().AddGoal(goalHistory.getMatch(), goalHistory.getTeam())) {
            return false;
        }
        TextConnectorUtils.saveToFile(goalHistoryMapper.ToDataList(allGoalHistories), goalHistoryFile);
        return true;
    }

    @Override
    public GoalHistoryDTO[] GetGoalHistories() {
        Path path = TextConnectorUtils.fullFilePath(goalHistoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        return goalHistoryMapper.ToDTOList(lines).toArray(new GoalHistoryDTO[0]);
    }

    @Override
    public GoalHistoryDTO[] GetGoalHistories(MatchDTO match) {
        Path path = TextConnectorUtils.fullFilePath(goalHistoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<GoalHistoryDTO> allGoalHistories = goalHistoryMapper.ToDTOList(lines);
        ArrayList<GoalHistoryDTO> matchGoalHistories = new ArrayList<>();
        for (GoalHistoryDTO gh : allGoalHistories) {
            if (gh.getMatch().getId() == match.getId()) {
                matchGoalHistories.add(gh);
            }
        }
        return matchGoalHistories.toArray(new GoalHistoryDTO[0]);
    }

    @Override
    public boolean DeleteGoalHistory(GoalHistoryDTO goalHistory) {
        Path path = TextConnectorUtils.fullFilePath(goalHistoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<GoalHistoryDTO> allGoalHistories = goalHistoryMapper.ToDTOList(lines);
        ArrayList<GoalHistoryDTO> matchGoalHistories = new ArrayList<>(List.of(GetGoalHistories(goalHistory.getMatch())));

        if (!matchGoalHistories.contains(goalHistory)) {
            return false;
        }

        allGoalHistories.remove(goalHistory);

        TextConnectorUtils.saveToFile(goalHistoryMapper.ToDataList(allGoalHistories), goalHistoryFile);
        return false;
    }
}
