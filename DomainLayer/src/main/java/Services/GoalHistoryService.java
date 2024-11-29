package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.GoalHistoryDTO;
import DomainModels.GoalHistoryDomainModel;
import DomainModels.MatchDomainModel;
import DomainModels.TeamDomainModel;
import Mappers.GoalHistoryDomainMapper;
import Mappers.MatchDomainMapper;

public class GoalHistoryService {
    private GoalHistoryDomainMapper mapper;
    private MatchDomainMapper matchMapper;

    public GoalHistoryService() {
        this.mapper = new GoalHistoryDomainMapper();
        this.matchMapper = new MatchDomainMapper();
    }

    public GoalHistoryDomainModel[] GetGoalHistories(MatchDomainModel match) {
        GoalHistoryDTO[] goalHistories = GlobalConfig
                .connection
                .getGoalHistoryDao()
                .GetGoalHistories(matchMapper.ToDTO(match));
        GoalHistoryDomainModel[] models = new GoalHistoryDomainModel[goalHistories.length];
        for (int i = 0; i < goalHistories.length; i++) {
            models[i] = mapper.ToDomain(goalHistories[i]);
        }
        return models;
    }

    public Boolean AddGoalHistory(GoalHistoryDomainModel goalHistory) {
        return GlobalConfig
                .connection
                .getGoalHistoryDao()
                .CreateGoalHistory(mapper.ToDTO(goalHistory));
    }

    public Boolean RemoveGoalHistory(GoalHistoryDomainModel goalHistory) {
        return GlobalConfig
                .connection
                .getGoalHistoryDao()
                .DeleteGoalHistory(mapper.ToDTO(goalHistory));
    }

}
