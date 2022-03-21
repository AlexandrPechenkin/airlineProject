package app.entities.dtos;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * DTO для класса AirlineManager.
 */
@Data
@ApiModel
public class AirlineManagerDTO {
    private long id;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    @Email(message = "Пожалуйста, введите корректный адрес почты.")
    private String email;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    private String password;

    private String roles;

    @NotEmpty(message = "Пожалуйста, заполните поле.")
    private String parkName;
}
