package DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoalHistoryDTO {
    private int minute;
    private PlayerDTO player;
    private TeamDTO team;
    private UserDTO creator;
}
