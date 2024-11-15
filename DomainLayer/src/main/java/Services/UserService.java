package Services;

import DataTransferObjects.UserDTO;
import DomainModels.UserDomainModel;
import Mappers.UserDomainMapper;
import DataAccess.Connectors.GlobalConfig;

public class UserService {
    private UserDomainMapper mapper;

    public UserService() {
        this.mapper = new UserDomainMapper();
    }

    public boolean CreateUser(String name, String surname, String email, String password, String role) {
        return
            GlobalConfig
                .connection
                .getUserDao()
                .CreateUser(name, surname, email, password, role);
    }

    public UserDomainModel Login(String email, String password) {
        UserDTO user = GlobalConfig
            .connection
            .getUserDao()
            .LoginUser(email, password);
        return mapper.ToDomain(user);
    }
}
