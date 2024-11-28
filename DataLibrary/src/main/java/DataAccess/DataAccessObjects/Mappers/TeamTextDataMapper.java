package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.TeamDTO;

public class TeamTextDataMapper extends IDataMapper<TeamDTO,String> {
    @Override
    public TeamDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new TeamDTO(
                Integer.parseInt(cols[0]),
                cols[1],
                cols[2],
                GlobalConfig.connection.getCategoryDao().GetCategory(cols[3]),
                GlobalConfig.connection.getLeagueDao().GetLeague(Integer.parseInt(cols[4]))
        );
    }

    @Override
    public String ToData(TeamDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getName());
        sb.append(GlobalConfig.separator);
        sb.append(data.getCountryCode());
        sb.append(GlobalConfig.separator);
        sb.append(data.getCategory().getName());
        sb.append(GlobalConfig.separator);
        sb.append(data.getLeague().getId());

        return sb.toString();
    }
}
