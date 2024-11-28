package Mappers;

import DataTransferObjects.PlayerDTO;
import DomainModels.PlayerDomainModel;

public class PlayerDomainMapper extends IDomainMapper<PlayerDomainModel, PlayerDTO> {
    @Override
    public PlayerDomainModel ToDomain(PlayerDTO data) {
        return new PlayerDomainModel(data.getId(),data.getName(), data.getSurname(), data.getBirthDate());
    }

    @Override
    public PlayerDTO ToDTO(PlayerDomainModel data) {
        return new PlayerDTO(data.getId(), data.getName(), data.getSurname(), data.getBirthDate());
    }
}
