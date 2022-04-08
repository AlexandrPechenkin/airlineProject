package app.entities.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

/**
 * Data Transfer Object - передающий данные из Passenger наружу.
 * @author Александр Данилов
 * @version 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class PassengerDTO extends UserDTO {

    @NotEmpty(message = "Поле firstName не должно быть пустым")
    private String firstName;

    @NotEmpty(message = "Поле lastName не должно быть пустым")
    private String lastName;

    private String middleName;

    @NotNull(message = "Поле dateOfBirth не должно быть пустым")
    @PastOrPresent
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    private PassportDTO passport;
}
