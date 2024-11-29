package Mappers;

import DataTransferObjects.GoalHistoryDTO;
import DomainModels.GoalHistoryDomainModel;

public class GoalHistoryDomainMapper extends IDomainMapper<GoalHistoryDomainModel, GoalHistoryDTO> {

    PlayerDomainMapper playerMapper = new PlayerDomainMapper();
    TeamDomainMapper teamMapper = new TeamDomainMapper();
    MatchDomainMapper matchMapper = new MatchDomainMapper();
    UserDomainMapper userMapper = new UserDomainMapper();



    @Override
    public GoalHistoryDomainModel ToDomain(GoalHistoryDTO data) {
        return new GoalHistoryDomainModel(
            data.getMinute(),
            playerMapper.ToDomain(data.getPlayer()),
            teamMapper.ToDomain(data.getTeam()),
            matchMapper.ToDomain(data.getMatch()),
            userMapper.ToDomain(data.getCreator())
        );
    }

    @Override
    public GoalHistoryDTO ToDTO(GoalHistoryDomainModel data) {
        return new GoalHistoryDTO(
            data.getMinute(),
            playerMapper.ToDTO(data.getPlayer()),
            teamMapper.ToDTO(data.getTeam()),
            matchMapper.ToDTO(data.getMatch()),
            userMapper.ToDTO(data.getCreator())
        );
    }
}
