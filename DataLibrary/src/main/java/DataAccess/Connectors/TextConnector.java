package DataAccess.Connectors;

import DataAccess.DataAccessObjects.Interface.*;
import DataAccess.DataAccessObjects.Text.UserTextDAO;

public class TextConnector implements IDataConnection {
    private ICategoryDAO categoryDao;
    private IFavouriteDAO favouriteDao;
    private IGoalHistoryDAO goalHistoryDao;
    private ILeagueDAO leagueDao;
    private IMatchDTO matchDao;
    private IPlayerHistoryDAO playerHistoryDao;
    private IPlayerDAO playerDao;
    private ITeamDAO teamDao;
    private IUserDAO userDao;

    public TextConnector() {
        this.userDao = new UserTextDAO();
    }


    @Override
    public ICategoryDAO getCategoryDao() {
        return null;
    }

    @Override
    public IFavouriteDAO getFavouriteDao() {
        return null;
    }

    @Override
    public IGoalHistoryDAO getGoalHistoryDao() {
        return null;
    }

    @Override
    public ILeagueDAO getLeagueDao() {
        return null;
    }

    @Override
    public IMatchDTO getMatchDao() {
        return null;
    }

    @Override
    public IPlayerHistoryDAO getPlayerHistoryDao() {
        return null;
    }

    @Override
    public IPlayerDAO getPlayerDao() {
        return null;
    }

    @Override
    public ITeamDAO getTeamDao() {
        return null;
    }

    @Override
    public IUserDAO getUserDao() {
        return userDao;
    }
}
