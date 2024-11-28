package Mappers;

import DataTransferObjects.PlayerDTO;
import DomainModels.CategoryDomainModel;
import DomainModels.PlayerDomainModel;

public class PlayerDomainMapper extends IDomainMapper<PlayerDomainModel, PlayerDTO> {
    private CategoryDomainMapper categoryMapper;


    public PlayerDomainMapper() {
        this.categoryMapper = new CategoryDomainMapper();
    }

    @Override
    public PlayerDomainModel ToDomain(PlayerDTO data) {
        return new PlayerDomainModel(data.getId(), data.getName(), data.getSurname(), data.getBirthDate(), categoryMapper.ToDomain(data.getCategory()));
    }

    @Override
    public PlayerDTO ToDTO(PlayerDomainModel data) {
        return new PlayerDTO(data.getId(), data.getName(), data.getSurname(), data.getBirthDate(), categoryMapper.ToDTO(data.getCategory()));
    }
}
