package DomainModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDomainModel {
    private String name;


    @Override
    public String toString() {
        return name;
    }
}
