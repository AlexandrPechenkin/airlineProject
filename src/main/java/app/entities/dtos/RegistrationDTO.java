package app.entities.dtos;

import app.entities.Seat;
import app.entities.Ticket;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * DTO для класса Registration.
 */
@Data
@ApiModel
public class RegistrationDTO {

    private long id;

    /*@NotEmpty(message = "Пожалуйста, укажите место")
    private Seat seat;*/

    private Ticket ticket;

    private String status;

    private LocalDateTime registrationDateTime;
}
