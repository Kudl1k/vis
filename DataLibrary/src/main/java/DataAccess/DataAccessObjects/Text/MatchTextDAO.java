package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.IMatchDAO;
import DataAccess.DataAccessObjects.Mappers.MatchTextDataMapper;
import DataTransferObjects.GoalHistoryDTO;
import DataTransferObjects.MatchDTO;

import java.nio.file.Path;
import java.util.ArrayList;

public class MatchTextDAO implements IMatchDAO {

    public static String matchFile = "Matches.csv";

    private MatchTextDataMapper matchMapper;

    public MatchTextDAO() {
        matchMapper = new MatchTextDataMapper();
    }

    @Override
    public boolean CreateMatch(MatchDTO match) {
        Path path = TextConnectorUtils.fullFilePath(matchFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<MatchDTO> matches = matchMapper.ToDTOList(lines);

        if (matches.stream().anyMatch(m -> m.getHomeTeam().equals(match.getHomeTeam()) && m.getAwayTeam().equals(match.getAwayTeam()) && m.getStartTime().equals(match.getStartTime()))) {
            return false;
        }

        int matchesCount = matches.size();
        int newId = matchesCount > 0 ? matches.get(matchesCount - 1).getId() + 1 : 1;

        match.setId(newId);

        matches.add(match);

        TextConnectorUtils.saveToFile(matchMapper.ToDataList(matches), matchFile);

        return true;
    }

    @Override
    public MatchDTO[] GetMatches() {
        Path path = TextConnectorUtils.fullFilePath(matchFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<MatchDTO> matches = matchMapper.ToDTOList(lines);

        return matches.toArray(new MatchDTO[0]);
    }

    @Override
    public MatchDTO[] GetMatchesByLeague(int leagueID) {
        Path path = TextConnectorUtils.fullFilePath(matchFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<MatchDTO> matches = matchMapper.ToDTOList(lines);

        return matches.stream().filter(m -> m.getLeague().getId() == leagueID).toArray(MatchDTO[]::new);
    }

    @Override
    public MatchDTO GetMatch(int matchID) {
        Path path = TextConnectorUtils.fullFilePath(matchFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<MatchDTO> matches = matchMapper.ToDTOList(lines);

        return matches.stream().filter(m -> m.getId() == matchID).findFirst().orElse(null);
    }

    @Override
    public boolean AddGoal(GoalHistoryDTO goal) {
        return false;
    }

    @Override
    public boolean ToggleFavorite(int matchID, int userID) {
        return false;
    }
}
