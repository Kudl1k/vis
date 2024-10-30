package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.PlayerDTO;

public class PlayerTextDataMapper extends IDataMapper<PlayerDTO,String> {

    @Override
    public PlayerDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new PlayerDTO(
            Integer.parseInt(cols[0]),
            cols[1],
            cols[2],
            cols[3]
        );
    }

    @Override
    public String ToData(PlayerDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getName());
        sb.append(GlobalConfig.separator);
        sb.append(data.getSurname());
        sb.append(GlobalConfig.separator);
        sb.append(data.getBirthDate());

        return sb.toString();
    }
}
