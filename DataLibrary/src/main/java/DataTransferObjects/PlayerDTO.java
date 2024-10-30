package DataTransferObjects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerDTO {
    private int id;
    private String name;
    private String surname;
    private String birthDate;
}
