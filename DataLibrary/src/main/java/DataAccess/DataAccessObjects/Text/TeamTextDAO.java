package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.ITeamDAO;
import DataAccess.DataAccessObjects.Mappers.TeamTextDataMapper;
import DataTransferObjects.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class TeamTextDAO implements ITeamDAO {
    public static String teamFile = "Team.csv";

    private TeamTextDataMapper teamManager;

    public TeamTextDAO() {
        teamManager = new TeamTextDataMapper();
    }


    @Override
    public boolean CreateTeam(TeamDTO team) {
        Path path = TextConnectorUtils.fullFilePath(teamFile);
        System.out.println(path.toString());
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<TeamDTO> teams = teamManager.ToDTOList(lines);

        int teamsCount = teams.size();
        System.out.println(teamsCount);
        int newId = teamsCount > 0 ? teams.get(teamsCount - 1).getId() + 1 : 1;

        team.setId(newId);

        teams.add(team);

        TextConnectorUtils.saveToFile(teamManager.ToDataList(teams), teamFile);

        return true;
    }

    @Override
    public TeamDTO GetTeam(int id) {
        Path path = TextConnectorUtils.fullFilePath(teamFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<TeamDTO> teams = teamManager.ToDTOList(lines);

        return teams.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public TeamDTO[] GetTeamsByLeague(int leagueID) {
        Path path = TextConnectorUtils.fullFilePath(teamFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<TeamDTO> teams = teamManager.ToDTOList(lines);

        return teams.stream().filter(t -> t.getLeague().getId() == leagueID).toArray(TeamDTO[]::new);
    }

    @Override
    public TeamDTO[] GetTeamsByCategory(CategoryDTO category) {
        Path path = TextConnectorUtils.fullFilePath(teamFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<TeamDTO> teams = teamManager.ToDTOList(lines);

        return teams.stream().filter(t -> Objects.equals(t.getCategory().getName(), category.getName())).toArray(TeamDTO[]::new);
    }
}
