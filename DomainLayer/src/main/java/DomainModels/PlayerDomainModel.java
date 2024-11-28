package DomainModels;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerDomainModel {
    private int id;
    private String name;
    private String surname;
    private String birthDate;
    private CategoryDomainModel category;

    public PlayerDomainModel(String name, String surname, String birthDate, CategoryDomainModel category) {
        this.id = -1;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.category = category;
    }

    @Override
    public String toString(){
        return name + " " + surname + " (" + birthDate + ")";
    }

}
