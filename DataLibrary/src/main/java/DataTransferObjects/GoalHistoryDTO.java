package DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GoalHistoryDTO {
    private int minute;
    private PlayerDTO player;
    private TeamDTO team;
    private MatchDTO match;
    private UserDTO creator;
}
