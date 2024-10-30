package DataTransferObjects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerHistoryDTO {
    private String startDate;
    private String endDate;
    private PlayerDTO player;
    private TeamDTO team;
}
