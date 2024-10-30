package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.CategoryDTO;
import DataTransferObjects.TeamDTO;

public class TeamTextDataManager extends IDataMapper<TeamDTO,String> {
    @Override
    public TeamDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new TeamDTO(
                Integer.parseInt(cols[0]),
                cols[1],
                cols[2],
                new CategoryTextDataMapper().ToDTO(cols[3]),
                new LeagueTextDataMapper().ToDTO(cols[4])
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
        sb.append(new CategoryTextDataMapper().ToData(data.getCategory()));
        sb.append(GlobalConfig.separator);
        sb.append(new LeagueTextDataMapper().ToData(data.getLeague()));

        return sb.toString();
    }
}
