package DataTransferObjects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MatchDTO {
    private int id;
    private TeamDTO homeTeam;
    private TeamDTO awayTeam;
    private int homeScore;
    private int awayScore;
    private String startTime;
    private String endTime;
    private int viewers;
    private String stadium;
    private CategoryDTO category;
    private LeagueDTO league;
    private UserDTO creator;
}