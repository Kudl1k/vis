package DataAccess.DataAccessObjects.Mappers;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.UserDTO;

public class UserTextDataMapper extends IDataMapper<UserDTO,String> {
    @Override
    public UserDTO ToDTO(String data) {
        String[] cols = data.split(GlobalConfig.separator);

        return new UserDTO(
                Integer.parseInt(cols[0]),
                cols[1],
                cols[2],
                cols[3],
                cols[4],
                cols[5]
        );
    }

    @Override
    public String ToData(UserDTO data) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getId());
        sb.append(GlobalConfig.separator);
        sb.append(data.getName());
        sb.append(GlobalConfig.separator);
        sb.append(data.getSurname());
        sb.append(GlobalConfig.separator);
        sb.append(data.getEmail());
        sb.append(GlobalConfig.separator);
        sb.append(data.getPassword());
        sb.append(GlobalConfig.separator);
        sb.append(data.getRole());

        return sb.toString();
    }
}
