package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.PlayerDTO;
import DataTransferObjects.PlayerHistoryDTO;

public interface IPlayerDAO {
    PlayerDTO GetPlayer(int playerID);
    boolean CreatePlayer(PlayerDTO player);
    boolean UpdatePlayer(PlayerDTO player);
    PlayerHistoryDTO[] GetPlayerHistory(int playerID);
}
