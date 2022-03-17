package app.entities.users.admin.dto;

import app.entities.users.user.dto.UserDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * DTO для класса Admin.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class AdminDTO extends UserDTO {
    private Long id;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    private String password;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    @Email(message = "Пожалуйста, введите корректный email-адрес")
    private String email;

    private String role;

    private String nickname;
}
