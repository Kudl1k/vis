package DataAccess.Connectors;


import DataAccess.DataAccessObjects.Interface.*;

public interface IDataConnection {
    public ICategoryDAO getCategoryDao();
    public IFavouriteDAO getFavouriteDao();
    public IGoalHistoryDAO getGoalHistoryDao();
    public ILeagueDAO getLeagueDao();
    public IMatchDAO getMatchDao();
    public IPlayerHistoryDAO getPlayerHistoryDao();
    public IPlayerDAO getPlayerDao();
    public ITeamDAO getTeamDao();
    public IUserDAO getUserDao();
}
