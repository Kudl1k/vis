package DataTransferObjects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PlayerDTO {
    private int id;
    private String name;
    private String surname;
    private String birthDate;
    private CategoryDTO category;
}
