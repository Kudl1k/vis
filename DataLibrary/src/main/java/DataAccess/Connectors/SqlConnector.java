package DataAccess.Connectors;

import DataAccess.DataAccessObjects.Interface.*;

public class SqlConnector implements IDataConnection{
    private ICategoryDAO categoryDao;
    private IFavouriteDAO favouriteDao;
    private IGoalHistoryDAO goalHistoryDao;
    private ILeagueDAO leagueDao;
    private IMatchDTO matchDao;
    private IPlayerHistoryDAO playerHistoryDao;
    private IPlayerDAO playerDao;
    private ITeamDAO teamDao;
    private IUserDAO userDao;

    public SqlConnector()
    {
//        this.categoryDao = new CategorySqlDAO();
//        this.favouriteDao = new FavouriteSqlDAO();
//        this.goalHistoryDao = new GoalHistorySqlDAO();
//        this.leagueDao = new LeagueSqlDAO();
//        this.matchDao = new MatchSqlDAO();
//        this.playerHistoryDao = new PlayerHistorySqlDAO();
//        this.playerDao = new PlayerSqlDAO();
//        this.teamDao = new TeamSqlDAO();
//        this.userDao = new UserSqlDAO();
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
        return null;
    }
}
