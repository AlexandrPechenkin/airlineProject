package app.entities.dtos;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * DTO для класса AirlineManager.
 */
@Data
@ApiModel
public class AirlineManagerDTO {
    private Long id;

    @NotEmpty(message = "Пожалуйста, заполните поле parkName.")
    private String parkName;
}
