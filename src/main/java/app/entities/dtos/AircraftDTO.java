package app.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object - передающий данные из Aircraft наружу.
 * @author Александр Данилов
 * @version 0.1
 */
@Data
@Jacksonized
@ApiModel
public class AircraftDTO {
    private Long id;

    @NotEmpty(message = "Поле brand не должно быть пустым")
    private String brand;

    @NotEmpty(message = "Поле model не должно быть пустым")
    private String model;

    @NotEmpty(message = "Поле boardNumber не должно быть пустым")
    private String boardNumber;

    @NotNull(message = "Поле productionYear не должно быть пустым")
    @PastOrPresent
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate productionYear;

    @NotNull(message = "Поле flyingRange не должно быть пустым")
    private Integer flyingRange;

    private List<CategoryDTO> categories;
}
