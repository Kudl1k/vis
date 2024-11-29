package DataAccess.Connectors;

import DataAccess.DataAccessObjects.Interface.*;
import DataAccess.DataAccessObjects.Text.*;

public class TextConnector implements IDataConnection {
    private ICategoryDAO categoryDao;
    private IFavouriteDAO favouriteDao;
    private IGoalHistoryDAO goalHistoryDao;
    private ILeagueDAO leagueDao;
    private IMatchDAO matchDao;
    private IPlayerHistoryDAO playerHistoryDao;
    private IPlayerDAO playerDao;
    private ITeamDAO teamDao;
    private IUserDAO userDao;

    public TextConnector() {
        this.userDao = new UserTextDAO();
        this.categoryDao = new CategoryTextDAO();
        this.leagueDao = new LeagueTextDAO();
        this.teamDao = new TeamTextDAO();
        this.matchDao = new MatchTextDAO();
        this.playerDao = new PlayerTextDAO();
        this.playerHistoryDao = new PlayerHistoryTextDAO();
        this.goalHistoryDao = new GoalHistoryTextDao();
    }


    @Override
    public ICategoryDAO getCategoryDao() {
        return categoryDao;
    }

    @Override
    public IFavouriteDAO getFavouriteDao() {
        return null;
    }

    @Override
    public IGoalHistoryDAO getGoalHistoryDao() {
        return goalHistoryDao;
    }

    @Override
    public ILeagueDAO getLeagueDao() {
        return leagueDao;
    }

    @Override
    public IMatchDAO getMatchDao() {
        return matchDao;
    }

    @Override
    public IPlayerHistoryDAO getPlayerHistoryDao() {
        return playerHistoryDao;
    }

    @Override
    public IPlayerDAO getPlayerDao() {
        return playerDao;
    }

    @Override
    public ITeamDAO getTeamDao() {
        return teamDao;
    }

    @Override
    public IUserDAO getUserDao() {
        return userDao;
    }
}
