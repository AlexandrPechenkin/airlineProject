package app.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
/**
 * Data Transfer Object - передающий данные из Passport наружу.
 * @author Александр Данилов
 * @version 0.1
 */
@Data
@ApiModel
public class PassportDTO {
    private Long id;

    @NotEmpty(message = "Поле паспортных данных: firstName не должно быть пустым")
    private String firstName;

    @NotEmpty(message = "Поле паспортных данных: lastName не должно быть пустым")
    private String lastName;

    @NotEmpty(message = "Поле паспортных данных: middleName не должно быть пустым")
    private String middleName;

    @NotEmpty(message = "Поле паспортных данных: gender не должно быть пустым")
    private String gender;

    @NotEmpty(message = "Поле паспортных данных: birthplace не должно быть пустым")
    private String birthplace;

    @NotEmpty(message = "Поле паспортных данных: residenceRegistration не должно быть пустым")
    private String residenceRegistration;

    @NotNull(message = "Поле паспортных данных: dateOfBirth не должно быть пустым")
    @PastOrPresent
    @DateTimeFormat(pattern = "dd.MM.yyyy")
//    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Поле паспортных данных: seriesAndNumber не должно быть пустым")
    @Pattern(regexp = "\\d{4}\\s\\d{6}")
    private String seriesAndNumber;
}
