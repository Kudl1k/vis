package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.IPlayerHistoryDAO;
import DataAccess.DataAccessObjects.Mappers.PlayerHistoryTextDataMapper;
import DataTransferObjects.PlayerDTO;
import DataTransferObjects.PlayerHistoryDTO;
import DataTransferObjects.TeamDTO;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlayerHistoryTextDAO implements IPlayerHistoryDAO {

    public static String playerHistoryFile = "PlayerHistory.csv";

    private PlayerHistoryTextDataMapper playerHistoryMapper;

    public PlayerHistoryTextDAO() {
        playerHistoryMapper = new PlayerHistoryTextDataMapper();
    }

    @Override
    public boolean addPlayerHistory(PlayerHistoryDTO playerHistory) {
        Path path = TextConnectorUtils.fullFilePath(playerHistoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<PlayerHistoryDTO> allPlayerHistories = playerHistoryMapper.ToDTOList(lines);
        ArrayList<PlayerHistoryDTO> playerHistories = new ArrayList<>(List.of(getPlayerHistory(playerHistory.getPlayer())));


        if (!playerHistories.isEmpty()) {
            PlayerHistoryDTO lastPlayerHistory = playerHistories.get(playerHistories.size() - 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate lastEndDate = LocalDate.parse(lastPlayerHistory.getEndDate(), formatter);
            LocalDate newStartDate = LocalDate.parse(playerHistory.getStartDate(), formatter);
            if (lastEndDate.isAfter(newStartDate)) {
                return false;
            }
        }

        allPlayerHistories.add(playerHistory);

        TextConnectorUtils.saveToFile(playerHistoryMapper.ToDataList(allPlayerHistories), playerHistoryFile);

        return true;
    }

    @Override
    public boolean removeLastPlayerHistory(PlayerDTO player) {
        Path path = TextConnectorUtils.fullFilePath(playerHistoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<PlayerHistoryDTO> playerHistories = playerHistoryMapper.ToDTOList(lines);

        if (playerHistories.isEmpty()) {
            return false;
        }

        PlayerHistoryDTO lastPlayerHistory = playerHistories.get(playerHistories.size() - 1);
        if (lastPlayerHistory.getPlayer().getId() != player.getId()) {
            return false;
        }

        playerHistories.remove(playerHistories.size() - 1);

        TextConnectorUtils.saveToFile(playerHistoryMapper.ToDataList(playerHistories), playerHistoryFile);

        return true;
    }

    @Override
    public PlayerHistoryDTO[] getPlayerHistory(PlayerDTO player) {
        Path path = TextConnectorUtils.fullFilePath(playerHistoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<PlayerHistoryDTO> playerHistories = playerHistoryMapper.ToDTOList(lines);

        return playerHistories.stream()
                .filter(playerHistory -> playerHistory.getPlayer().getId() == player.getId())
                .toArray(PlayerHistoryDTO[]::new);
    }

    @Override
    public PlayerHistoryDTO[] getPlayersHistoryInTeam(TeamDTO team) {
        Path path = TextConnectorUtils.fullFilePath(playerHistoryFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<PlayerHistoryDTO> playerHistories = playerHistoryMapper.ToDTOList(lines);

        LocalDate currentDate = LocalDate.now();

        return playerHistories.stream()
                .filter(playerHistory -> playerHistory.getTeam().getId() == team.getId())
                .filter(playerHistory -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate startDate = LocalDate.parse(playerHistory.getStartDate(), formatter);
                    LocalDate endDate = LocalDate.parse(playerHistory.getEndDate(), formatter);
                    return startDate.isBefore(currentDate) && endDate.isAfter(currentDate);
                })
                .toArray(PlayerHistoryDTO[]::new);
    }
}
