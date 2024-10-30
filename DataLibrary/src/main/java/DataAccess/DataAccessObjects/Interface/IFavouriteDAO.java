package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.MatchDTO;
import DataTransferObjects.UserDTO;

public interface IFavouriteDAO {
    boolean CreateFavourite(int customerID, int itemID);
    boolean DeleteFavourite(int customerID, int itemID);
    int[] GetFavourites(int customerID);
    boolean IsFavourite(int customerID, int itemID);
    UserDTO[] GetFavouriteUsers(int itemID);
    MatchDTO[] GetFavouriteMatches(int customerID);
}
