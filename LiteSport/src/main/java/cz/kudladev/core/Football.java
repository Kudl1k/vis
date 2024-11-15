package cz.kudladev.core;

import DomainModels.LeagueDomainModel;
import Services.LeagueService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Football {

    private LeagueService leagueService;
    private LeagueDomainModel[] leagues;



    public Football() {
        this.leagueService = new LeagueService();
        LoadLeagues();
        System.out.println("Football class created");
        System.out.println("Leagues size: " + leagues.length);
        for (LeagueDomainModel league : leagues) {
            System.out.println(league.toString());
        }
    }

    public void LoadLeagues() {
        this.leagues = leagueService.GetLeagues("Football");
    }
}
