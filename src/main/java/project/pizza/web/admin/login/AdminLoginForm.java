package project.pizza.web.admin.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdminLoginForm {

    @NotEmpty
    private String emailId;

    @NotEmpty
    private String password;
}
