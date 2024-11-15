package DomainModels;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDomainModel {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
    private String last_log;
    private String created_at;

    UserDomainModel(String name, String surname, String email, String password, String role) {
        this.id = -1;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.last_log = System.currentTimeMillis() + "";
        this.created_at = System.currentTimeMillis() + "";
    }

}