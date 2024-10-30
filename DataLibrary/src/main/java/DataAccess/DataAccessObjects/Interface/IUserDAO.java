package DataAccess.DataAccessObjects.Interface;

public interface IUserDAO {
    boolean CreateUser(String name, String surname, String email, String password, Character role);
}
