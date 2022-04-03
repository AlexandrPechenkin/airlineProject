package app.entities.dtos;

import app.entities.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserDTO {

    protected Long id;

    @NotEmpty(message = "Поле email не должно быть пустым")
    @Email(message = "Введите корректный email адрес")
    protected String email;

    @NotEmpty(message = "Поле password не должно быть пустым")
    protected String password;

    protected Set<Role> roles;

}
