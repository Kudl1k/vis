package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.IPlayerDAO;
import DataAccess.DataAccessObjects.Mappers.PlayerTextDataMapper;
import DataTransferObjects.PlayerDTO;
import DataTransferObjects.PlayerHistoryDTO;
import DataTransferObjects.TeamDTO;

import java.nio.file.Path;
import java.util.ArrayList;

public class PlayerTextDAO implements IPlayerDAO {

    public static String playerFile = "Players.csv";

    private PlayerTextDataMapper playerMapper;

    public PlayerTextDAO() {
        playerMapper = new PlayerTextDataMapper();
    }

    @Override
    public PlayerDTO GetPlayer(int playerID) {
        Path path = TextConnectorUtils.fullFilePath(playerFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        return playerMapper.ToDTOList(lines).stream().filter(p -> p.getId() == playerID).findFirst().orElse(null);
    }

    @Override
    public boolean CreatePlayer(PlayerDTO player) {
        Path path = TextConnectorUtils.fullFilePath(playerFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<PlayerDTO> players = playerMapper.ToDTOList(lines);

        if (players.stream().anyMatch(p -> p.getName().equals(player.getName()) && p.getSurname().equals(player.getSurname()) && p.getBirthDate().equals(player.getBirthDate()))) {
            return false;
        }

        int playersCount = players.size();
        int newId = playersCount > 0 ? players.get(playersCount - 1).getId() + 1 : 1;

        player.setId(newId);

        players.add(player);

        TextConnectorUtils.saveToFile(playerMapper.ToDataList(players), playerFile);

        return true;
    }

    @Override
    public PlayerDTO[] GetPlayers() {
        Path path = TextConnectorUtils.fullFilePath(playerFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        return playerMapper.ToDTOList(lines).toArray(new PlayerDTO[0]);
    }

    @Override
    public boolean UpdatePlayer(PlayerDTO player) {
        return false;
    }

    @Override
    public PlayerHistoryDTO[] GetPlayerHistory(int playerID) {
        return new PlayerHistoryDTO[0];
    }

    @Override
    public PlayerDTO[] GetPlayersByTeam(TeamDTO team) {
        return new PlayerDTO[0];
    }
}
