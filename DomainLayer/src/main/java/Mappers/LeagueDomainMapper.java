package Mappers;

import DataTransferObjects.CategoryDTO;
import DataTransferObjects.LeagueDTO;
import DomainModels.LeagueDomainModel;

public class LeagueDomainMapper extends IDomainMapper<LeagueDomainModel, LeagueDTO> {
    @Override
    public LeagueDomainModel ToDomain(LeagueDTO data) {
        LeagueDomainModel model = new LeagueDomainModel();
        model.setId(data.getId());
        model.setName(data.getName());
        model.setCountryCode(data.getCountryCode());
        model.setCategoryDomainModel(new CategoryDomainMapper().ToDomain(data.getCategoryDTO()));
        return model;
    }

    @Override
    public LeagueDTO ToDTO(LeagueDomainModel data) {
        CategoryDTO categoryDTO = data.getCategory() != null ? new CategoryDTO(data.getCategory().getName()) : null;
        return new LeagueDTO(
                data.getId(),
                data.getName(),
                data.getCountryCode(),
                categoryDTO
        );
    }
}