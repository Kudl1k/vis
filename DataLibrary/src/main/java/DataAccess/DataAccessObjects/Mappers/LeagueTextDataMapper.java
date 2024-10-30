package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.LeagueDTO;

public class LeagueTextDataMapper extends IDataMapper<LeagueDTO,String> {
    @Override
    public LeagueDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new LeagueDTO(
                cols[0],
                cols[1]
        );
    }

    @Override
    public String ToData(LeagueDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getName());
        sb.append(GlobalConfig.separator);
        sb.append(data.getCountryCode());

        return sb.toString();
    }
}
