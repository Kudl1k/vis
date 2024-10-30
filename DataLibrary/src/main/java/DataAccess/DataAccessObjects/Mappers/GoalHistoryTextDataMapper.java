package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.GoalHistoryDTO;

public class GoalHistoryTextDataMapper extends IDataMapper<GoalHistoryDTO,String> {
    @Override
    public GoalHistoryDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new GoalHistoryDTO(
                Integer.parseInt(cols[0]),
                new PlayerTextDataMapper().ToDTO(cols[1]),
                new TeamTextDataManager().ToDTO(cols[2]),
                new UserTextDataMapper().ToDTO(cols[3])
        );
    }

    @Override
    public String ToData(GoalHistoryDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getMinute());
        sb.append(GlobalConfig.separator);
        sb.append(new PlayerTextDataMapper().ToData(data.getPlayer()));
        sb.append(GlobalConfig.separator);
        sb.append(new TeamTextDataManager().ToData(data.getTeam()));
        sb.append(GlobalConfig.separator);
        sb.append(new UserTextDataMapper().ToData(data.getCreator()));

        return sb.toString();
    }
}
