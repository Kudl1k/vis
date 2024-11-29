package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.GoalHistoryDTO;

public class GoalHistoryTextDataMapper extends IDataMapper<GoalHistoryDTO,String> {
    @Override
    public GoalHistoryDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new GoalHistoryDTO(
                Integer.parseInt(cols[0]),
                GlobalConfig.connection.getPlayerDao().GetPlayer(Integer.parseInt(cols[1])),
                GlobalConfig.connection.getTeamDao().GetTeam(Integer.parseInt(cols[2])),
                GlobalConfig.connection.getMatchDao().GetMatch(Integer.parseInt(cols[3])),
                GlobalConfig.connection.getUserDao().GetUser(Integer.parseInt(cols[4]))
        );
    }

    @Override
    public String ToData(GoalHistoryDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getMinute());
        sb.append(GlobalConfig.separator);
        sb.append(data.getPlayer().getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getTeam().getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getMatch().getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getCreator().getId());

        return sb.toString();
    }
}
