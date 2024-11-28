package DomainModels;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamDomainModel {
    private int id;
    private String name;
    private String countryCode;
    private LeagueDomainModel leagueDomainModel;
    private CategoryDomainModel categoryDomainModel;

    public TeamDomainModel(String name, String countryCode, LeagueDomainModel leagueDomainModel, CategoryDomainModel categoryDomainModel) {
        this.id = -1;
        this.name = name;
        this.countryCode = countryCode;
        this.leagueDomainModel = leagueDomainModel;
        this.categoryDomainModel = categoryDomainModel;
    }

    @Override
    public String toString(){
        return name + "(" + countryCode + "-" + leagueDomainModel.toString() + "-" + categoryDomainModel.toString() + ")";
    }
}
