package DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamDTO {
    private int id;
    private String name;
    private String countryCode;
    private CategoryDTO category;
    private LeagueDTO league;
}
