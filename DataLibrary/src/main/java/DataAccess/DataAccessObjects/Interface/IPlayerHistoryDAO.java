package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.PlayerDTO;
import DataTransferObjects.PlayerHistoryDTO;
import DataTransferObjects.TeamDTO;

public interface IPlayerHistoryDAO {
    boolean addPlayerHistory(PlayerHistoryDTO playerHistory);
    boolean removeLastPlayerHistory(PlayerDTO player);
    PlayerHistoryDTO[] getPlayerHistory(PlayerDTO player);
    PlayerHistoryDTO[] getPlayersHistoryInTeam(TeamDTO team);
}
