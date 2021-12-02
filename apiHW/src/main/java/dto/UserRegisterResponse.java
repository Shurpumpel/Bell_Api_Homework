package dto;

import lombok.Data;

@Data
public class UserRegisterResponse {
    private Integer id;
    private String token;
}
