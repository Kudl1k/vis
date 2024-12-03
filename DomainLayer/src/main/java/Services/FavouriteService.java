package Services;

import DataAccess.Connectors.GlobalConfig;
import DataTransferObjects.FavouriteDTO;
import DomainModels.FavouriteDomainModel;
import DomainModels.MatchDomainModel;
import DomainModels.UserDomainModel;
import Mappers.FavouriteDomainMapper;
import Mappers.MatchDomainMapper;
import Mappers.UserDomainMapper;

import java.util.ArrayList;

public class FavouriteService {
    private FavouriteDomainMapper mapper;
    private MatchDomainMapper matchMapper = new MatchDomainMapper();
    private UserDomainMapper userMapper = new UserDomainMapper();


    public FavouriteService() {
        this.mapper = new FavouriteDomainMapper();
    }

    public Boolean AddFavourite(FavouriteDomainModel favourite) {
        return GlobalConfig
                .connection
                .getFavouriteDao()
                .CreateFavourite(mapper.ToDTO(favourite));
    }

    public Boolean RemoveFavourite(FavouriteDomainModel favourite) {
        return GlobalConfig
                .connection
                .getFavouriteDao()
                .RemoveFavourite(mapper.ToDTO(favourite));
    }

    public FavouriteDomainModel[] GetFavourites() {
        FavouriteDTO[] favourites = GlobalConfig
                .connection
                .getFavouriteDao()
                .GetFavourites();

        FavouriteDomainModel[] models = new FavouriteDomainModel[favourites.length];
        for (int i = 0; i < favourites.length; i++) {
            models[i] = mapper.ToDomain(favourites[i]);
        }
        return models;
    }

    public FavouriteDomainModel[] GetFavouritesByMatch(MatchDomainModel match) {
        FavouriteDTO[] favourites = GlobalConfig
                .connection
                .getFavouriteDao()
                .GetFavouritesByMatch(matchMapper.ToDTO(match));

        FavouriteDomainModel[] models = new FavouriteDomainModel[favourites.length];
        for (int i = 0; i < favourites.length; i++) {
            models[i] = mapper.ToDomain(favourites[i]);
        }
        return models;
    }

    public FavouriteDomainModel[] GetFavouritesByUser(UserDomainModel user) {
        FavouriteDTO[] favourites = GlobalConfig
                .connection
                .getFavouriteDao()
                .GetFavouritesByUser(userMapper.ToDTO(user));

        FavouriteDomainModel[] models = new FavouriteDomainModel[favourites.length];
        for (int i = 0; i < favourites.length; i++) {
            models[i] = mapper.ToDomain(favourites[i]);
        }
        return models;
    }

    public Boolean GetFavouriteByUserAndMatch(UserDomainModel user, MatchDomainModel match) {
        return GlobalConfig
                .connection
                .getFavouriteDao()
                .GetFavouriteByUserAndMatch(userMapper.ToDTO(user), matchMapper.ToDTO(match));
    }
}
