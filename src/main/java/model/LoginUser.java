package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LoginUser {
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;


}
