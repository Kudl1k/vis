package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.PlayerDTO;
import DataTransferObjects.PlayerHistoryDTO;
import DataTransferObjects.TeamDTO;

public interface IPlayerDAO {
    PlayerDTO GetPlayer(int playerID);
    boolean CreatePlayer(PlayerDTO player);
    boolean UpdatePlayer(PlayerDTO player);
    PlayerHistoryDTO[] GetPlayerHistory(int playerID);
    PlayerDTO[] GetPlayersByTeam(TeamDTO team);
}
