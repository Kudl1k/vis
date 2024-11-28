package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.PlayerHistoryDTO;
import DomainModels.PlayerDomainModel;
import DomainModels.PlayerHistoryDomainModel;
import Mappers.PlayerDomainMapper;
import Mappers.PlayerHistoryDomainMapper;

public class PlayerHistoryService {
    private PlayerHistoryDomainMapper mapper;


    public PlayerHistoryService() {
        this.mapper = new PlayerHistoryDomainMapper();
    }

    public boolean CreatePlayerHistory(PlayerHistoryDomainModel model) {
        return GlobalConfig
                .connection
                .getPlayerHistoryDao()
                .addPlayerHistory(mapper.ToDTO(model));
    }

    public boolean RemoveLastPlayerHistory(PlayerHistoryDomainModel model) {
        return GlobalConfig
                .connection
                .getPlayerHistoryDao()
                .removeLastPlayerHistory(new PlayerDomainMapper().ToDTO(model.getPlayer()));
    }

    public PlayerHistoryDomainModel[] GetPlayerHistory(PlayerDomainModel model) {
        PlayerHistoryDTO[] playerHistoryDTOs = GlobalConfig
                .connection
                .getPlayerHistoryDao()
                .getPlayerHistory(new PlayerDomainMapper().ToDTO(model));
        PlayerHistoryDomainModel[] playerHistoryDomainModels = new PlayerHistoryDomainModel[playerHistoryDTOs.length];
        for (int i = 0; i < playerHistoryDTOs.length; i++) {
            playerHistoryDomainModels[i] = mapper.ToDomain(playerHistoryDTOs[i]);
        }
        return playerHistoryDomainModels;
    }
}
