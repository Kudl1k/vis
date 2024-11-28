package cz.kudladev.core;

import DomainModels.CategoryDomainModel;
import DomainModels.LeagueDomainModel;
import DomainModels.MatchDomainModel;
import DomainModels.TeamDomainModel;
import Services.CategoryService;
import Services.LeagueService;
import Services.MatchService;
import Services.TeamService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Football {

    private CategoryService categoryService;
    private LeagueService leagueService;
    private TeamService teamService;
    private MatchService matchService;

    private LeagueDomainModel[] leagues;
    private TeamDomainModel[] teams;


    public Football() {
        this.categoryService = new CategoryService();
        this.leagueService = new LeagueService();
        this.teamService = new TeamService();
        this.matchService = new MatchService();
        LoadLeagues();
    }

    public void LoadLeagues() {
        CategoryDomainModel category = categoryService.GetCategory("Football");
        this.leagues = leagueService.GetLeagues(category);
        if (leagues == null) {
            System.out.println("Leagues are null");
            return;
        }
        System.out.println("Football class created");
        System.out.println("Leagues size: " + leagues.length);
    }

    public TeamDomainModel[] LoadTeams(LeagueDomainModel league) {
        return teamService.GetTeamsByLeague(league);
    }

    public MatchDomainModel[] LoadMatches(LeagueDomainModel league) {
        if (league == null) {
            return matchService.GetMatches();
        } else {
            return matchService.GetMatchesByLeague(league);
        }
    }
}
