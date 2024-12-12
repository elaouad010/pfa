package projetPFA.gestionFonct.Login.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projetPFA.gestionFonct.Login.User.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String cin;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

}
