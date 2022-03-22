package app.entities.dtos;

import app.entities.Role;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * DTO для класса Admin.
 */
@Data
@ApiModel
public class AdminDTO {
    private long id;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    private String password;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    @Email(message = "Пожалуйста, введите корректный email-адрес")
    private String email;

    private Set<Role> roles;

    private String nickname;
}
