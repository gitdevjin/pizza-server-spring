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

    public Member(String email, String password, String firstName, String lastName, String address, String role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.role = role;
    }

    public Member() {
    }
}
