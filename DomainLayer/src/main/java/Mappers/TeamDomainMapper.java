package Mappers;

import DataTransferObjects.TeamDTO;
import DomainModels.TeamDomainModel;

public class TeamDomainMapper extends IDomainMapper<TeamDomainModel, TeamDTO> {


    @Override
    public TeamDomainModel ToDomain(TeamDTO data) {
        return new TeamDomainModel(
            data.getId(),
            data.getName(),
            data.getCountryCode(),
            new LeagueDomainMapper().ToDomain(data.getLeague()),
            new CategoryDomainMapper().ToDomain(data.getCategory())
        );
    }

    @Override
    public TeamDTO ToDTO(TeamDomainModel domainModel) {
        return new TeamDTO(
                domainModel.getId(),
                domainModel.getName(),
                domainModel.getCountryCode(),
                new CategoryDomainMapper().ToDTO(domainModel.getCategoryDomainModel()),
                new LeagueDomainMapper().ToDTO(domainModel.getLeagueDomainModel())
        );
    }
}
