package app.entities.flight.dto;

import app.entities.flight.FlightStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FlightDTO {
    private Long id;
    @NotNull(message = "Дата вылета не может быть пустой")
    @PastOrPresent
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate departureDate;
    @NotNull(message = "Время вылета не может быть пустым")
    @PastOrPresent
    @DateTimeFormat(pattern = "hh:mm")
    @JsonFormat(pattern = "hh:mm")
    private LocalTime departureTime;
    @NotNull(message = "Дата и время прилета не может быть пустой")
    @PastOrPresent
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime arrivalDateTime;
    //    @NotEmpty(message = "Место вылета не может быть пустым")
    //    private Destination from;
    //    @NotEmpty(message = "Место прилета не может быть пустым")
    //    private Destination to;
    @NotEmpty(message = "Статус не может быть пустым")
    private FlightStatus flightStatus;
    //    private Map<Category, List<Seat>> seatsByCategory;
    //    @NotEmpty(message = "Нужно указать самолет")
    //    private Aircraft aircraft;

    /**
     * в будущем удалить
     */
    private String to;
    private String from;
}
