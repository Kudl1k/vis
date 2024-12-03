package DataAccess.Connectors;

import DataAccess.DataAccessObjects.Interface.*;
import DataAccess.DataAccessObjects.Sql.*;

public class SqlConnector implements IDataConnection{
    private ICategoryDAO categoryDao;
    private IFavouriteDAO favouriteDao;
    private IGoalHistoryDAO goalHistoryDao;
    private ILeagueDAO leagueDao;
    private IMatchDAO matchDao;
    private IPlayerHistoryDAO playerHistoryDao;
    private IPlayerDAO playerDao;
    private ITeamDAO teamDao;
    private IUserDAO userDao;

    public SqlConnector()
    {
        this.categoryDao = new CategorySqlDAO();
//        this.favouriteDao = new FavouriteSqlDAO();
        this.goalHistoryDao = new GoalHistorySqlDAO();
        this.leagueDao = new LeagueSqlDAO();
        this.matchDao = new MatchSqlDAO();
        this.playerHistoryDao = new PlayerHistorySqlDAO();
        this.playerDao = new PlayerSqlDAO();
        this.teamDao = new TeamSqlDAO();
        this.userDao = new UserSqlDAO();
        this.favouriteDao = new FavouriteSqlDAO();
    }


    @Override
    public ICategoryDAO getCategoryDao() {
        return this.categoryDao;
    }

    @Override
    public IFavouriteDAO getFavouriteDao() {
        return this.favouriteDao;
    }

    @Override
    public IGoalHistoryDAO getGoalHistoryDao() {
        return this.goalHistoryDao;
    }

    @Override
    public ILeagueDAO getLeagueDao() {
        return this.leagueDao;
    }

    @Override
    public IMatchDAO getMatchDao() {
        return this.matchDao;
    }

    @Override
    public IPlayerHistoryDAO getPlayerHistoryDao() {
        return this.playerHistoryDao;
    }

    @Override
    public IPlayerDAO getPlayerDao() {
        return this.playerDao;
    }

    @Override
    public ITeamDAO getTeamDao() {
        return this.teamDao;
    }

    @Override
    public IUserDAO getUserDao() {
        return this.userDao;
    }
}
