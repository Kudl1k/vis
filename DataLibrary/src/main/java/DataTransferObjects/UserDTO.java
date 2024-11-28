package DataTransferObjects;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
    private String last_log;
    private String created_at;
}
