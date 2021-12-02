package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLogin {

    private String email;
    private String password;

    public UserLogin(String email) {
        this.email = email;
    }
}
