package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.LeagueDTO;
import DomainModels.LeagueDomainModel;
import Mappers.LeagueDomainMapper;

public class LeagueService {
    private LeagueDomainMapper mapper;

    public LeagueService() {
        this.mapper = new LeagueDomainMapper();
    }

    public boolean CreateLeague(LeagueDomainModel model) {
        return GlobalConfig
                .connection
                .getLeagueDao()
                .CreateLeague(mapper.ToDTO(model));
    }

    public LeagueDomainModel[] GetLeagues(String category) {
        LeagueDTO[] leagues = GlobalConfig
            .connection
            .getLeagueDao()
            .GetLeaguesByCategory(category);
        LeagueDomainModel[] models = new LeagueDomainModel[leagues.length];
        for (int i = 0; i < leagues.length; i++) {
            models[i] = mapper.ToDomain(leagues[i]);
        }
        return models;
    }
}