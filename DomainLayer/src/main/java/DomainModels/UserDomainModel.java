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

    public static UserDomainModel fromString(String part) {
        String[] parts = part.split(",");
        if (parts.length == 8) {
            return new UserDomainModel(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
        }
        return null;
    }
    public static String toSaveString(UserDomainModel user) {
        return user.id + "," + user.name + "," + user.surname + "," + user.email + "," + user.password + "," + user.role + "," + user.last_log + "," + user.created_at;
    }


    @Override
    public String toString(){
        return name + " " + surname + " (" + email + ")";
    }

}