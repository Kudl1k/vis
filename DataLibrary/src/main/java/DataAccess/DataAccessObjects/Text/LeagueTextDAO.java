package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.ILeagueDAO;
import DataAccess.DataAccessObjects.Mappers.LeagueTextDataMapper;
import DataTransferObjects.CategoryDTO;
import DataTransferObjects.LeagueDTO;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class LeagueTextDAO implements ILeagueDAO {
    public static String leagueFile = "Leagues.csv";

    public static String TeamLeagueFile = "TeamLeagues.csv";

    private LeagueTextDataMapper leagueMapper;

    public LeagueTextDAO() {
        leagueMapper = new LeagueTextDataMapper();
    }

    @Override
    public boolean CreateLeague(LeagueDTO league) {
        Path path = TextConnectorUtils.fullFilePath(leagueFile);
        System.out.println(path.toString());
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<LeagueDTO> leagues = leagueMapper.ToDTOList(lines);

        if (leagues.stream().anyMatch(l -> l.getName().equals(league.getName()) && l.getCountryCode().equals(league.getCountryCode()))) {
            return false;
        }

        leagues.add(league);

        TextConnectorUtils.saveToFile(leagueMapper.ToDataList(leagues), leagueFile);

        return true;
    }

    @Override
    public LeagueDTO[] GetLeagues() {
        Path path = TextConnectorUtils.fullFilePath(leagueFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<LeagueDTO> leagues = leagueMapper.ToDTOList(lines);

        leagues.forEach(league -> league.setCategoryDTO(new CategoryDTO(league.getCategoryDTO().getName())));

        return leagues.toArray(new LeagueDTO[0]);
    }

    @Override
    public LeagueDTO[] GetLeaguesByCategory(String category) {
        Path path = TextConnectorUtils.fullFilePath(leagueFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<LeagueDTO> leagues = leagueMapper.ToDTOList(lines);

        return leagues.stream().filter(l -> Objects.equals(l.getCategoryDTO().getName(), category)).toArray(LeagueDTO[]::new);
    }

    @Override
    public LeagueDTO[] GetLeaguesByCountry(String countryCode) {
        Path path = TextConnectorUtils.fullFilePath(leagueFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<LeagueDTO> leagues = leagueMapper.ToDTOList(lines);

        return leagues.stream().filter(l -> l.getCountryCode().equals(countryCode)).toArray(LeagueDTO[]::new);
    }

    @Override
    public boolean AddTeam(LeagueDTO league, int teamID) {
        Path path = TextConnectorUtils.fullFilePath(leagueFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<LeagueDTO> leagues = leagueMapper.ToDTOList(lines);

        LeagueDTO leagueToUpdate = leagues.stream().filter(l -> ((l.getName() == league.getName()) && (l.getCountryCode() == league.getCountryCode() && (l.getCategoryDTO() == league.getCategoryDTO()))  )).findFirst().orElse(null);

        if (leagueToUpdate == null) {
            return false;
        }
        Path pathTeamLeague = TextConnectorUtils.fullFilePath(TeamLeagueFile);
        Iterable<String> linesTeamLeague = TextConnectorUtils.loadFile(pathTeamLeague);


        return true;
    }

    @Override
    public boolean RemoveTeam(LeagueDTO league, int teamID) {
        return false;
    }
}
