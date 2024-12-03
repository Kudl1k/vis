package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.FavouriteDTO;

public class FavouriteTextDataMapper extends IDataMapper<FavouriteDTO,String> {

    @Override
    public FavouriteDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new FavouriteDTO(
            GlobalConfig.connection.getUserDao().GetUser(Integer.parseInt(cols[0])),
            GlobalConfig.connection.getMatchDao().GetMatch(Integer.parseInt(cols[1]))
        );
    }

    @Override
    public String ToData(FavouriteDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getUser().getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getMatch().getId());

        return sb.toString();
    }
}
