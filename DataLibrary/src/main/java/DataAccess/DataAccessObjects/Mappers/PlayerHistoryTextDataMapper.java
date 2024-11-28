package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.PlayerHistoryDTO;

public class PlayerHistoryTextDataMapper extends IDataMapper<PlayerHistoryDTO,String> {
    @Override
    public PlayerHistoryDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new PlayerHistoryDTO(
            cols[0],
            cols[1],
            new PlayerTextDataMapper().ToDTO(cols[2]),
            new TeamTextDataMapper().ToDTO(cols[3])
        );
    }

    @Override
    public String ToData(PlayerHistoryDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getStartDate());
        sb.append(GlobalConfig.separator);
        sb.append(data.getEndDate());
        sb.append(GlobalConfig.separator);
        sb.append(new PlayerTextDataMapper().ToData(data.getPlayer()));
        sb.append(GlobalConfig.separator);
        sb.append(new TeamTextDataMapper().ToData(data.getTeam()));

        return sb.toString();
    }
}
