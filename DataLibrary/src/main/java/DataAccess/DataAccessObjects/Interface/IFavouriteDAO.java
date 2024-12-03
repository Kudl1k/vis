package DataAccess.DataAccessObjects.Interface;

import DataTransferObjects.FavouriteDTO;
import DataTransferObjects.MatchDTO;
import DataTransferObjects.UserDTO;

public interface IFavouriteDAO {

    boolean CreateFavourite(FavouriteDTO favourite);
    boolean RemoveFavourite(FavouriteDTO favourite);

    FavouriteDTO[] GetFavourites();
    FavouriteDTO[] GetFavouritesByMatch(MatchDTO match);
    FavouriteDTO[] GetFavouritesByUser(UserDTO user);
    boolean GetFavouriteByUserAndMatch(UserDTO user, MatchDTO match);
}
