package DataAccess.Connectors;

import DataAccess.DataAccessObjects.Interface.*;
import DataAccess.DataAccessObjects.Sql.CategorySqlDAO;
import DataAccess.DataAccessObjects.Sql.GoalHistorySqlDAO;
import DataAccess.DataAccessObjects.Sql.LeagueSqlDAO;
import DataAccess.DataAccessObjects.Sql.UserSqlDAO;

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
//        this.matchDao = new MatchSqlDAO();
//        this.playerHistoryDao = new PlayerHistorySqlDAO();
//        this.playerDao = new PlayerSqlDAO();
//        this.teamDao = new TeamSqlDAO();
        this.userDao = new UserSqlDAO();
    }


    @Override
    public ICategoryDAO getCategoryDao() {
        return this.categoryDao;
    }

    @Override
    public IFavouriteDAO getFavouriteDao() {
        return null;
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
        return this.userDao;
    }
}
