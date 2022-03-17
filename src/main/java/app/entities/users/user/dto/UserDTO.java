package app.entities.users.user.dto;

import io.swagger.annotations.ApiModel;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * DTO для класса User.
 */
@Component
@ApiModel
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    @Email(message = "Пожалуйста, введите корректный email.")
    private String email;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    private String password;

    private String roles;
}
