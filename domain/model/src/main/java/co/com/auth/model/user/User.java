package co.com.auth.model.user;
import co.com.auth.model.role.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String id;
    private String email;
    private String password;
    private int rol;
    private int attempts;
}
