package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.UserDTO;

public interface IUserDAO {
    boolean CreateUser(String name, String surname, String email, String password, String role);
    UserDTO LoginUser(String email, String password);
    boolean UpdateUser(UserDTO user);
}
