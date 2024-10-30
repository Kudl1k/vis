package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.MatchDTO;

public class MatchTextDataManager extends IDataMapper<MatchDTO,String> {

    @Override
    public MatchDTO ToDTO(String data) {
        String cols[] = data.split(";");

        return new MatchDTO(
                Integer.parseInt(cols[0]),
                new TeamTextDataManager().ToDTO(cols[1]),
                new TeamTextDataManager().ToDTO(cols[2]),
                Integer.parseInt(cols[3]),
                Integer.parseInt(cols[4]),
                cols[5],
                cols[6],
                Integer.parseInt(cols[7]),
                cols[8],
                new CategoryTextDataMapper().ToDTO(cols[9]),
                new LeagueTextDataMapper().ToDTO(cols[10]),
                new UserTextDataMapper().ToDTO(cols[11])
        );
    }

    @Override
    public String ToData(MatchDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getId());
        sb.append(GlobalConfig.separator);
        sb.append(new TeamTextDataManager().ToData(data.getHomeTeam()));
        sb.append(GlobalConfig.separator);
        sb.append(new TeamTextDataManager().ToData(data.getAwayTeam()));
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
        sb.append(new CategoryTextDataMapper().ToData(data.getCategory()));
        sb.append(GlobalConfig.separator);
        sb.append(new LeagueTextDataMapper().ToData(data.getLeague()));
        sb.append(GlobalConfig.separator);
        sb.append(new UserTextDataMapper().ToData(data.getCreator()));

        return sb.toString();
    }
}
