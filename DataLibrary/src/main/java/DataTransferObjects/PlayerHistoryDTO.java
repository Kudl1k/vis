package DataTransferObjects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PlayerHistoryDTO {
    private String startDate;
    private String endDate;
    private PlayerDTO player;
    private TeamDTO team;
}
