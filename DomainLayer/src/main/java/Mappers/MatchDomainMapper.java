package Mappers;

import DataTransferObjects.MatchDTO;
import DomainModels.MatchDomainModel;

public class MatchDomainMapper extends IDomainMapper<MatchDomainModel, MatchDTO> {
    private final TeamDomainMapper teamDomainMapper;
    private final CategoryDomainMapper categoryDomainMapper;
    private final LeagueDomainMapper leagueDomainMapper;
    private final UserDomainMapper userDomainMapper;

    public MatchDomainMapper() {
        this.teamDomainMapper = new TeamDomainMapper();
        this.categoryDomainMapper = new CategoryDomainMapper();
        this.leagueDomainMapper = new LeagueDomainMapper();
        this.userDomainMapper = new UserDomainMapper();
    }

    @Override
    public MatchDomainModel ToDomain(MatchDTO data) {
        return new MatchDomainModel(
                data.getId(),
                teamDomainMapper.ToDomain(data.getHomeTeam()),
                teamDomainMapper.ToDomain(data.getAwayTeam()),
                data.getHomeScore(),
                data.getAwayScore(),
                data.getStartTime(),
                data.getEndTime(),
                data.getViewers(),
                data.getStadium(),
                categoryDomainMapper.ToDomain(data.getCategory()),
                leagueDomainMapper.ToDomain(data.getLeague()),
                userDomainMapper.ToDomain(data.getCreator())
        );
    }

    @Override
    public MatchDTO ToDTO(MatchDomainModel data) {
        return new MatchDTO(
                data.getId(),
                teamDomainMapper.ToDTO(data.getHomeTeam()),
                teamDomainMapper.ToDTO(data.getAwayTeam()),
                data.getHomeScore(),
                data.getAwayScore(),
                data.getStartTime(),
                data.getEndTime(),
                data.getViewers(),
                data.getStadium(),
                categoryDomainMapper.ToDTO(data.getCategory()),
                leagueDomainMapper.ToDTO(data.getLeague()),
                userDomainMapper.ToDTO(data.getCreator())
        );
    }
}
