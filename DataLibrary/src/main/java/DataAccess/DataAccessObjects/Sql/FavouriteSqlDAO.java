package DataAccess.DataAccessObjects.Sql;

import DataAccess.Connectors.GlobalConfig;
import DataAccess.DataAccessObjects.Interface.IFavouriteDAO;
import DataTransferObjects.FavouriteDTO;
import DataTransferObjects.MatchDTO;
import DataTransferObjects.UserDTO;

import java.sql.Connection;
import java.util.ArrayList;

import static DataAccess.Connectors.SqlConnectorUtils.getConnection;

public class FavouriteSqlDAO implements IFavouriteDAO {


    @Override
    public boolean CreateFavourite(FavouriteDTO favourite) {
        try (Connection connection = getConnection()){
            if (connection != null) {
                String query = "INSERT INTO favourites (user_id, match_id) VALUES (?, ?)";
                var statement = connection.prepareStatement(query);
                statement.setInt(1, favourite.getUser().getId());
                statement.setInt(2, favourite.getMatch().getId());
                statement.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean RemoveFavourite(FavouriteDTO favourite) {
        try (Connection connection = getConnection()){
            if (connection != null) {
                String query = "DELETE FROM favourites WHERE user_id = ? AND match_id = ?";
                var statement = connection.prepareStatement(query);
                statement.setInt(1, favourite.getUser().getId());
                statement.setInt(2, favourite.getMatch().getId());
                statement.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public FavouriteDTO[] GetFavourites() {
        try (Connection connection = getConnection()){
            if (connection != null) {
                String query = "SELECT * FROM favourites";
                var statement = connection.prepareStatement(query);
                var result = statement.executeQuery();

                ArrayList<FavouriteDTO> favourites = new ArrayList<>();

                while (result.next()) {
                    var favourite = new FavouriteDTO(
                            GlobalConfig.connection.getUserDao().GetUser(result.getInt("user_id")),
                            GlobalConfig.connection.getMatchDao().GetMatch(result.getInt("match_id"))
                    );
                    favourites.add(favourite);
                }
                if (!favourites.isEmpty()) {
                    return favourites.toArray(new FavouriteDTO[0]);
                } else {
                    return new FavouriteDTO[0];
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new FavouriteDTO[0];
    }

    @Override
    public FavouriteDTO[] GetFavouritesByMatch(MatchDTO match) {
        try (Connection connection = getConnection()){
            if (connection != null) {
                String query = "SELECT * FROM favourites WHERE match_id = ?";
                var statement = connection.prepareStatement(query);
                statement.setInt(1, match.getId());
                var result = statement.executeQuery();

                ArrayList<FavouriteDTO> favourites = new ArrayList<>();

                while (result.next()) {
                    var favourite = new FavouriteDTO(
                            GlobalConfig.connection.getUserDao().GetUser(result.getInt("user_id")),
                            GlobalConfig.connection.getMatchDao().GetMatch(result.getInt("match_id"))
                    );
                    favourites.add(favourite);
                }
                if (!favourites.isEmpty()) {
                    return favourites.toArray(new FavouriteDTO[0]);
                } else {
                    return new FavouriteDTO[0];
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new FavouriteDTO[0];
    }

    @Override
    public FavouriteDTO[] GetFavouritesByUser(UserDTO user) {
        try (Connection connection = getConnection()){
            if (connection != null) {
                String query = "SELECT * FROM favourites WHERE user_id = ?";
                var statement = connection.prepareStatement(query);
                statement.setInt(1, user.getId());
                var result = statement.executeQuery();

                ArrayList<FavouriteDTO> favourites = new ArrayList<>();

                while (result.next()) {
                    var favourite = new FavouriteDTO(
                            GlobalConfig.connection.getUserDao().GetUser(result.getInt("user_id")),
                            GlobalConfig.connection.getMatchDao().GetMatch(result.getInt("match_id"))
                    );
                    favourites.add(favourite);
                }
                if (!favourites.isEmpty()) {
                    return favourites.toArray(new FavouriteDTO[0]);
                } else {
                    return new FavouriteDTO[0];
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new FavouriteDTO[0];
    }

    @Override
    public boolean GetFavouriteByUserAndMatch(UserDTO user, MatchDTO match) {
        try (Connection connection = getConnection()){
            if (connection != null) {
                String query = "SELECT * FROM favourites WHERE user_id = ? AND match_id = ?";
                var statement = connection.prepareStatement(query);
                statement.setInt(1, user.getId());
                statement.setInt(2, match.getId());
                var result = statement.executeQuery();

                if (result.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }


}
