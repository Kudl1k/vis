package DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FavouriteDTO {
    private UserDTO user;
    private MatchDTO match;
}
