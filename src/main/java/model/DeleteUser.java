package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DeleteUser {
    @Getter
    @Setter
    private String token;
}
