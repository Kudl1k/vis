package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.FavouriteDTO;
import DataTransferObjects.MatchDTO;

public class FavouriteTextDataMapper extends IDataMapper<FavouriteDTO,String> {

    @Override
    public FavouriteDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new FavouriteDTO(
            new UserTextDataMapper().ToDTO(cols[0]),
            new MatchTextDataManager().ToDTO(cols[1])
        );
    }

    @Override
    public String ToData(FavouriteDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(new UserTextDataMapper().ToData(data.getUser()));
        sb.append(GlobalConfig.separator);
        sb.append(new MatchTextDataManager().ToData(data.getMatch()));

        return sb.toString();
    }
}
