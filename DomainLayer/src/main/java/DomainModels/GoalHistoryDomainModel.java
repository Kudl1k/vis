package DomainModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GoalHistoryDomainModel {
    private int minute;
    private PlayerDomainModel player;
    private TeamDomainModel team;
    private MatchDomainModel match;
    private UserDomainModel creator;

    public GoalHistoryDomainModel(int minute, PlayerDomainModel player, TeamDomainModel team, MatchDomainModel match, UserDomainModel creator) {
        this.minute = minute;
        this.player = player;
        this.team = team;
        this.match = match;
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Goal scored by " + player + " in " + minute + " minute";
    }
}
