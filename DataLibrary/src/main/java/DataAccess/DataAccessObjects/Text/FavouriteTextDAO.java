package DataAccess.DataAccessObjects.Text;

import DataAccess.Connectors.TextConnectorUtils;
import DataAccess.DataAccessObjects.Interface.IFavouriteDAO;
import DataAccess.DataAccessObjects.Mappers.FavouriteTextDataMapper;
import DataTransferObjects.FavouriteDTO;
import DataTransferObjects.MatchDTO;
import DataTransferObjects.UserDTO;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FavouriteTextDAO implements IFavouriteDAO {

    public static String favouriteFile = "Favourite.csv";

    private FavouriteTextDataMapper favouriteMapper;

    public FavouriteTextDAO() {
        favouriteMapper = new FavouriteTextDataMapper();
    }

    @Override
    public boolean CreateFavourite(FavouriteDTO favourite) {
        Path path = TextConnectorUtils.fullFilePath(favouriteFile);

        ArrayList<FavouriteDTO> favourites = new ArrayList<>(List.of(GetFavourites()));

        for (FavouriteDTO favouriteCurrent : favourites) {
            if (favouriteCurrent.getUser().getId() == favouriteCurrent.getUser().getId() && favouriteCurrent.getMatch().getId() == favourite.getMatch().getId()) {
                return false;
            }
        }


        favourites.add(favourite);

        TextConnectorUtils.saveToFile(favouriteMapper.ToDataList(favourites), favouriteFile);
        return true;
    }

    @Override
    public boolean RemoveFavourite(FavouriteDTO favourite) {
        Path path = TextConnectorUtils.fullFilePath(favouriteFile);

        ArrayList<FavouriteDTO> favourites = new ArrayList<>(List.of(GetFavourites()));

        for (FavouriteDTO currentFavourite : favourites) {
            if (currentFavourite.getUser().getId() == favourite.getUser().getId() && currentFavourite.getMatch().getId() == favourite.getMatch().getId()) {
                favourites.remove(currentFavourite);
                TextConnectorUtils.saveToFile(favouriteMapper.ToDataList(favourites), favouriteFile);
                return true;
            }
        }
        return false;
    }

    @Override
    public FavouriteDTO[] GetFavourites() {
        Path path = TextConnectorUtils.fullFilePath(favouriteFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        return favouriteMapper.ToDTOList(lines).toArray(new FavouriteDTO[0]);
    }

    @Override
    public FavouriteDTO[] GetFavouritesByMatch(MatchDTO match) {
        Path path = TextConnectorUtils.fullFilePath(favouriteFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<FavouriteDTO> allFavourites = favouriteMapper.ToDTOList(lines);
        ArrayList<FavouriteDTO> matchFavourites = new ArrayList<>();
        for (FavouriteDTO favourite : allFavourites) {
            if (favourite.getMatch().getId() == match.getId()) {
                matchFavourites.add(favourite);
            }
        }
        return matchFavourites.toArray(new FavouriteDTO[0]);
    }

    @Override
    public FavouriteDTO[] GetFavouritesByUser(UserDTO user) {
        Path path = TextConnectorUtils.fullFilePath(favouriteFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<FavouriteDTO> allFavourites = favouriteMapper.ToDTOList(lines);
        ArrayList<FavouriteDTO> userFavourites = new ArrayList<>();
        for (FavouriteDTO favourite : allFavourites) {
            if (favourite.getUser().getId() == user.getId()) {
                userFavourites.add(favourite);
            }
        }
        return userFavourites.toArray(new FavouriteDTO[0]);
    }

    @Override
    public boolean GetFavouriteByUserAndMatch(UserDTO user, MatchDTO match) {
        Path path = TextConnectorUtils.fullFilePath(favouriteFile);
        Iterable<String> lines = TextConnectorUtils.loadFile(path);
        ArrayList<FavouriteDTO> allFavourites = favouriteMapper.ToDTOList(lines);
        for (FavouriteDTO favourite : allFavourites) {
            if (favourite.getUser().getId() == user.getId() && favourite.getMatch().getId() == match.getId()) {
                return true;
            }
        }
        return false;
    }
}
