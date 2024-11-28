package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.PlayerDTO;
import DomainModels.PlayerDomainModel;
import DomainModels.TeamDomainModel;
import Mappers.PlayerDomainMapper;
import Mappers.TeamDomainMapper;

public class PlayerService {
    private PlayerDomainMapper mapper;
    private TeamDomainMapper teamMapper;

    public PlayerService() {
        this.mapper = new PlayerDomainMapper();
        this.teamMapper = new TeamDomainMapper();
    }

    public boolean CreatePlayer(PlayerDomainModel model) {
        return GlobalConfig
                .connection
                .getPlayerDao()
                .CreatePlayer(mapper.ToDTO(model));
    }

    public PlayerDomainModel GetPlayer(int playerID) {
        return mapper.ToDomain(GlobalConfig
                .connection
                .getPlayerDao()
                .GetPlayer(playerID));
    }

    public PlayerDomainModel GetPlayersByTeam(TeamDomainModel team) {
        PlayerDTO[] players = GlobalConfig
                .connection
                .getPlayerDao()
                .GetPlayersByTeam(teamMapper.ToDTO(team));
        return mapper.ToDomain(players[0]);
    }
}
