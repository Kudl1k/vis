package DataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
}
