package app.entities.dtos;

import app.entities.FlightStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FlightDTO {
    private Long id;
    @NotNull(message = "Дата вылета не может быть пустой")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
//    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate departureDate;
    @NotNull(message = "Время вылета не может быть пустым")
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;
    @NotNull(message = "Дата прилета не может быть пустой")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime arrivalDateTime;
    //    @NotEmpty(message = "Место вылета не может быть пустым")
    //    private Destination from;
    //    @NotEmpty(message = "Место прилета не может быть пустым")
    //    private Destination to;
    @NotNull(message = "Статус не может быть пустым")
    private FlightStatus flightStatus;
    //    private Map<Category, List<Seat>> seatsByCategory;
    //    @NotEmpty(message = "Нужно указать самолет")
    //    private Aircraft aircraft;

    /**
     * в будущем удалить
     */
    private String destinationTo;
    private String destinationFrom;
}
