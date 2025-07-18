package DataTransferObjects;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LeagueDTO {
    private int id;
    private String name;
    private String countryCode;
    private CategoryDTO categoryDTO;

    public LeagueDTO(String name, String countryCode, CategoryDTO categoryDTO) {
        this.name = name;
        this.countryCode = countryCode;
        this.categoryDTO = categoryDTO;
    }



    public void loadCategoryDTO() {
        if (categoryDTO != null) {
            categoryDTO.getName();
        } else {
            // Handle the case where categoryDTO is null
            System.out.println("CategoryDTO is null");
        }
    }
}
