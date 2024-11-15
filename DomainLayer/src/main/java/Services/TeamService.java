package Services;

import DataAccess.Connectors.GlobalConfig;
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
}
