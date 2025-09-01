package co.com.auth.model.user;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String id;
    private String email;
    private String password;
    private String role;
}
