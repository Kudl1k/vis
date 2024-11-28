package DomainModels;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchDomainModel {
    private int id;
    private TeamDomainModel homeTeam;
    private TeamDomainModel awayTeam;
    private int homeScore;
    private int awayScore;
    private String startTime;
    private String endTime;
    private int viewers;
    private String stadium;
    private CategoryDomainModel category;
    private LeagueDomainModel league;
    private UserDomainModel creator;

    public MatchDomainModel(TeamDomainModel homeTeam, TeamDomainModel awayTeam, int homeScore, int awayScore, String startTime, String endTime, int viewers, String stadium, CategoryDomainModel category, LeagueDomainModel league, UserDomainModel creator) {
        this.id = -1;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.startTime = startTime;
        this.endTime = endTime;
        this.viewers = viewers;
        this.stadium = stadium;
        this.category = category;
        this.league = league;
        this.creator = creator;
    }

    @Override
    public String toString(){
        return homeTeam.toString() + " vs " + awayTeam.toString() + " (" + homeScore + "-" + awayScore + ") at " + stadium + " on " + startTime + " by " + creator.toString();
    }
}
