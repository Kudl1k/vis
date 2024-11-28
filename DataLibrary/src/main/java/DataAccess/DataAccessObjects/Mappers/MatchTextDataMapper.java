package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.MatchDTO;

public class MatchTextDataMapper extends IDataMapper<MatchDTO,String> {

    @Override
    public MatchDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new MatchDTO(
                Integer.parseInt(cols[0]),
                GlobalConfig.connection.getTeamDao().GetTeam(Integer.parseInt(cols[1])),
                GlobalConfig.connection.getTeamDao().GetTeam(Integer.parseInt(cols[2])),
                Integer.parseInt(cols[3]),
                Integer.parseInt(cols[4]),
                cols[5],
                cols[6],
                Integer.parseInt(cols[7]),
                cols[8],
                GlobalConfig.connection.getCategoryDao().GetCategory(cols[9]),
                GlobalConfig.connection.getLeagueDao().GetLeague(Integer.parseInt(cols[10])),
                GlobalConfig.connection.getUserDao().GetUser(Integer.parseInt(cols[11]))
        );
    }

    @Override
    public String ToData(MatchDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getHomeTeam().getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getAwayTeam().getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getHomeScore());
        sb.append(GlobalConfig.separator);
        sb.append(data.getAwayScore());
        sb.append(GlobalConfig.separator);
        sb.append(data.getStartTime());
        sb.append(GlobalConfig.separator);
        sb.append(data.getEndTime());
        sb.append(GlobalConfig.separator);
        sb.append(data.getViewers());
        sb.append(GlobalConfig.separator);
        sb.append(data.getStadium());
        sb.append(GlobalConfig.separator);
        sb.append(data.getCategory().getName());
        sb.append(GlobalConfig.separator);
        sb.append(data.getLeague().getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getCreator().getId());

        return sb.toString();
    }
}
