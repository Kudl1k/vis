package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.IMatchDAO;
import DataAccess.DataAccessObjects.Mappers.MatchTextDataMapper;
import DataTransferObjects.MatchDTO;
import DataTransferObjects.TeamDTO;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    public boolean AddGoal(MatchDTO match, TeamDTO team) {
        Path path = TextConnectorUtils.fullFilePath(matchFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<MatchDTO> matches = matchMapper.ToDTOList(lines);

        MatchDTO matchToUpdate = matches.stream().filter(m -> m.getId() == match.getId()).findFirst().orElse(null);

        if (matchToUpdate == null) {
            return false;
        }

        if (team.getId() == matchToUpdate.getHomeTeam().getId()) {
            System.out.println("Adding goal to home team");
            matchToUpdate.setHomeScore(matchToUpdate.getHomeScore() + 1);
        } else if (team.getId() == matchToUpdate.getAwayTeam().getId()) {
            System.out.println("Adding goal to away team");
            matchToUpdate.setAwayScore(matchToUpdate.getAwayScore() + 1);
        } else {
            System.out.println("Team not found");
            return false;
        }

        TextConnectorUtils.saveToFile(matchMapper.ToDataList(matches), matchFile);

        return true;
    }

    @Override
    public boolean RemoveGoal(MatchDTO match, TeamDTO team) {
        Path path = TextConnectorUtils.fullFilePath(matchFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<MatchDTO> matches = matchMapper.ToDTOList(lines);

        MatchDTO matchToUpdate = matches.stream().filter(m -> m.getId() == match.getId()).findFirst().orElse(null);

        if (matchToUpdate == null) {
            return false;
        }

        if (team.equals(matchToUpdate.getHomeTeam())) {
            matchToUpdate.setHomeScore(matchToUpdate.getHomeScore() - 1);
        } else if (team.equals(matchToUpdate.getAwayTeam())) {
            matchToUpdate.setAwayScore(matchToUpdate.getAwayScore() - 1);
        } else {
            return false;
        }

        TextConnectorUtils.saveToFile(matchMapper.ToDataList(matches), matchFile);

        return false;
    }


    @Override
    public boolean ToggleFavorite(int matchID, int userID) {
        return false;
    }

    @Override
    public boolean EndMatch(MatchDTO match) {
        Path path = TextConnectorUtils.fullFilePath(matchFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<MatchDTO> matches = matchMapper.ToDTOList(lines);

        MatchDTO matchToUpdate = matches.stream().filter(m -> m.getId() == match.getId()).findFirst().orElse(null);

        if (matchToUpdate == null) {
            return false;
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String outputFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(outputFormat);
        String formattedTimestamp = sdf.format(timestamp);

        matchToUpdate.setEndTime(formattedTimestamp);

        TextConnectorUtils.saveToFile(matchMapper.ToDataList(matches), matchFile);

        return true;
    }
}
