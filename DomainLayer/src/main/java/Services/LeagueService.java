package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.LeagueDTO;
import DomainModels.CategoryDomainModel;
import DomainModels.LeagueDomainModel;
import Mappers.CategoryDomainMapper;
import Mappers.LeagueDomainMapper;

public class LeagueService {
    private LeagueDomainMapper mapper;
    private CategoryDomainMapper categoryMapper;

    public LeagueService() {
        this.mapper = new LeagueDomainMapper();
        this.categoryMapper = new CategoryDomainMapper();
    }

    public boolean CreateLeague(LeagueDomainModel model) {
        return GlobalConfig
                .connection
                .getLeagueDao()
                .CreateLeague(mapper.ToDTO(model));
    }

    public LeagueDomainModel[] GetLeagues(CategoryDomainModel category) {
        LeagueDTO[] leagues = GlobalConfig
            .connection
            .getLeagueDao()
            .GetLeaguesByCategory(categoryMapper.ToDTO(category));
        LeagueDomainModel[] models = new LeagueDomainModel[leagues.length];
        for (int i = 0; i < leagues.length; i++) {
            models[i] = mapper.ToDomain(leagues[i]);
        }
        return models;
    }
}