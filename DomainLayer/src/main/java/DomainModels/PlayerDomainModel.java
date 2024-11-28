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

    public PlayerDomainModel(String name, String surname, String birthDate) {
        this.id = -1;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    @Override
    public String toString(){
        return name + " " + surname + " (" + birthDate + ")";
    }

}
