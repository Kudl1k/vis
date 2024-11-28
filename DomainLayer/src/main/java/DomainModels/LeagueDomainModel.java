package DomainModels;

import Services.LeagueService;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LeagueDomainModel {
    private int id;
    private String name;
    private String countryCode;
    private String category;
    private CategoryDomainModel categoryDomainModel;

    public LeagueDomainModel(String name, String countryCode, String category) {
        this.name = name;
        this.countryCode = countryCode;
        this.category = category;
    }
    public LeagueDomainModel(String name, String countryCode, CategoryDomainModel categoryDomainModel) {
        this.name = name;
        this.countryCode = countryCode;
        this.categoryDomainModel = categoryDomainModel;
    }

    public CategoryDomainModel getCategory() {
        if (categoryDomainModel == null) {
            categoryDomainModel = loadCategoryDomainModel();
        }
        return categoryDomainModel;
    }

    private CategoryDomainModel loadCategoryDomainModel() {
        return new CategoryDomainModel(category);
    }

    @Override
    public String toString(){
        return name + "(" + countryCode + "-" + categoryDomainModel.toString() + ")";
    }
}