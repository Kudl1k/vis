package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.IUserDAO;
import DataAccess.DataAccessObjects.Mappers.UserTextDataMapper;
import DataTransferObjects.UserDTO;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserTextDAO implements IUserDAO {
    public static String userFile = "Users.csv";

    private UserTextDataMapper userMapper;

    public UserTextDAO() {
        userMapper = new UserTextDataMapper();
    }

    @Override
    public boolean CreateUser(String name, String surname, String email, String password, String role) {
        Path path = TextConnectorUtils.fullFilePath(userFile);
        System.out.println(path.toString());
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<UserDTO> users = userMapper.ToDTOList(lines);

        int customersCount = users.size();
        System.out.println(customersCount);
        int newId = customersCount > 0 ? users.get(customersCount - 1).getId() + 1 : 1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = LocalDateTime.now().format(formatter);

        UserDTO user = new UserDTO(
                newId,
                name,
                surname,
                email,
                password,
                role,
                formattedTimestamp,
                formattedTimestamp
        );

        users.add(user);

        TextConnectorUtils.saveToFile(userMapper.ToDataList(users), userFile);

        return true;
    }

    @Override
    public UserDTO LoginUser(String email, String password) {
        Path path = TextConnectorUtils.fullFilePath(userFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<UserDTO> users = userMapper.ToDTOList(lines);

        for (UserDTO user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedTimestamp = LocalDateTime.now().format(formatter);
                user.setLast_log(formattedTimestamp);
                UpdateUser(user);
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean UpdateUser(UserDTO user) {
        Path path = TextConnectorUtils.fullFilePath(userFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<UserDTO> users = userMapper.ToDTOList(lines);

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                TextConnectorUtils.saveToFile(userMapper.ToDataList(users), userFile);
                return true;
            }
        }
        return false;
    }
}