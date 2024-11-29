package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.MatchDTO;
import DomainModels.LeagueDomainModel;
import DomainModels.MatchDomainModel;
import Mappers.MatchDomainMapper;

public class MatchService {
    private MatchDomainMapper mapper;

    public MatchService() {
        this.mapper = new MatchDomainMapper();
    }

    public boolean CreateMatch(MatchDomainModel model) {
        return GlobalConfig
                .connection
                .getMatchDao()
                .CreateMatch(mapper.ToDTO(model));
    }

    public MatchDomainModel GetMatch(int id) {
        return mapper.ToDomain(GlobalConfig
                .connection
                .getMatchDao()
                .GetMatch(id));
    }

    public MatchDomainModel[] GetMatches() {
        MatchDTO[] matches = GlobalConfig
                .connection
                .getMatchDao()
                .GetMatches();
        MatchDomainModel[] models = new MatchDomainModel[matches.length];
        for (int i = 0; i < matches.length; i++) {
            models[i] = mapper.ToDomain(matches[i]);
        }
        return models;
    }

    public MatchDomainModel[] GetMatchesByLeague(LeagueDomainModel league) {
        MatchDTO[] matches = GlobalConfig
                .connection
                .getMatchDao()
                .GetMatchesByLeague(league.getId());
        MatchDomainModel[] models = new MatchDomainModel[matches.length];
        for (int i = 0; i < matches.length; i++) {
            models[i] = mapper.ToDomain(matches[i]);
        }
        return models;
    }

    public boolean EndMatch(MatchDomainModel match) {
        return GlobalConfig
                .connection
                .getMatchDao()
                .EndMatch(mapper.ToDTO(match));
    }


}
