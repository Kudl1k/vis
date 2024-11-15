package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.LeagueDTO;

public class LeagueTextDataMapper extends IDataMapper<LeagueDTO,String> {
    @Override
    public LeagueDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);
        LeagueDTO league = new LeagueDTO();
        league.setId(Integer.parseInt(cols[0]));
        league.setName(cols[1]);
        league.setCountryCode(cols[2]);
        league.setCategoryDTO(new CategoryTextDataMapper().ToDTO(cols[3]));

        return league;
    }

    @Override
    public String ToData(LeagueDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getName());
        sb.append(GlobalConfig.separator);
        sb.append(data.getCountryCode());
        sb.append(GlobalConfig.separator);
        sb.append(data.getCategoryDTO().getName());

        return sb.toString();
    }
}
