package Mappers;

import DataTransferObjects.FavouriteDTO;
import DomainModels.FavouriteDomainModel;

public class FavouriteDomainMapper extends IDomainMapper<FavouriteDomainModel, FavouriteDTO> {
    private UserDomainMapper userMapper;
    private MatchDomainMapper matchMapper;

    public FavouriteDomainMapper() {
        userMapper = new UserDomainMapper();
        matchMapper = new MatchDomainMapper();
    }

    @Override
    public FavouriteDomainModel ToDomain(FavouriteDTO data) {
        return new FavouriteDomainModel(userMapper.ToDomain(data.getUser()), matchMapper.ToDomain(data.getMatch()));
    }

    @Override
    public FavouriteDTO ToDTO(FavouriteDomainModel data) {
        return new FavouriteDTO(userMapper.ToDTO(data.getUser()), matchMapper.ToDTO(data.getMatch()));
    }
}
