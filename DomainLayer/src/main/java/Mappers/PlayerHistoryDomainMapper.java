package Mappers;

import DataTransferObjects.PlayerHistoryDTO;
import DomainModels.PlayerHistoryDomainModel;

public class PlayerHistoryDomainMapper extends IDomainMapper<PlayerHistoryDomainModel, PlayerHistoryDTO> {
    @Override
    public PlayerHistoryDomainModel ToDomain(PlayerHistoryDTO data) {
        return new PlayerHistoryDomainModel(
            data.getStartDate(),
            data.getEndDate(),
            new PlayerDomainMapper().ToDomain(data.getPlayer()),
            new TeamDomainMapper().ToDomain(data.getTeam())
        );
    }

    @Override
    public PlayerHistoryDTO ToDTO(PlayerHistoryDomainModel data) {
        return new PlayerHistoryDTO(
            data.getStartDate(),
            data.getEndDate(),
            new PlayerDomainMapper().ToDTO(data.getPlayer()),
            new TeamDomainMapper().ToDTO(data.getTeam())
        );
    }
}
