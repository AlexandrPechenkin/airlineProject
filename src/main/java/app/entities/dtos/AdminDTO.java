package app.entities.dtos;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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

    private String roles;

    private String nickname;
}
