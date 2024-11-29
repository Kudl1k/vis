package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.IGoalHistoryDAO;
import DataAccess.DataAccessObjects.Mappers.GoalHistoryTextDataMapper;
import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GoalHistoryTextDao implements IGoalHistoryDAO {

    public static String goalHistoryFile = "GoalHistory.csv";

    private GoalHistoryTextDataMapper goalHistoryMapper;

    public GoalHistoryTextDao() {
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

        for (GoalHistoryDTO gh : allGoalHistories) {
            System.out.println(gh.toString());
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
        return new GoalHistoryDTO[0];
    }

    @Override
    public boolean DeleteGoalHistory(GoalHistoryDTO goalHistory) {
        Path path = TextConnectorUtils.fullFilePath(goalHistoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<GoalHistoryDTO> allGoalHistories = goalHistoryMapper.ToDTOList(lines);
        return false;
    }
}
