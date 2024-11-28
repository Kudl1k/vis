package DomainModels;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PlayerHistoryDomainModel {
    private String startDate;
    private String endDate;
    private PlayerDomainModel player;
    private TeamDomainModel team;

    public PlayerHistoryDomainModel(String startDate, String endDate, PlayerDomainModel player, TeamDomainModel team) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.player = player;
        this.team = team;
    }

    @Override
    public String toString(){
        return player.getName() + " " + player.getSurname() + "-" + team.toString() + " (" + startDate + " -> " + endDate + ")";
    }


}
