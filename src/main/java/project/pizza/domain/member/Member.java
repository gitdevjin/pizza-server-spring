package project.pizza.domain.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Member {

    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String role;

}
