package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.TeamDTO;
import DomainModels.LeagueDomainModel;
import DomainModels.TeamDomainModel;
import Mappers.TeamDomainMapper;

public class TeamService {
    private TeamDomainMapper mapper;

    public TeamService() {
        this.mapper = new TeamDomainMapper();
    }

    public boolean CreateTeam(TeamDomainModel model) {

        return
                GlobalConfig
                .connection
                .getTeamDao()
                .CreateTeam(mapper.ToDTO(model));
    }

    public TeamDomainModel[] GetTeamsByLeague(LeagueDomainModel league) {
        TeamDTO[] teams = GlobalConfig
                .connection
                .getTeamDao()
                .GetTeamsByLeague(league.getId());
        TeamDomainModel[] models = new TeamDomainModel[teams.length];
        for (int i = 0; i < teams.length; i++) {
            models[i] = mapper.ToDomain(teams[i]);
        }
        return models;
    }
}
