package DomainModels;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class FavouriteDomainModel {
    private UserDomainModel user;
    private MatchDomainModel match;
}
