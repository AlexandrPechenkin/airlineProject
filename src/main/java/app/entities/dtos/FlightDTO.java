package app.entities.dtos;

import app.entities.Aircraft;
import app.entities.Destination;
import app.entities.FlightStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FlightDTO {
    private Long id;
    @NotNull(message = "Дата вылета не может быть пустой")
    private LocalDate departureDate;
    @NotNull(message = "Время вылета не может быть пустым")
    private LocalTime departureTime;
    @NotNull(message = "Дата прилета не может быть пустой")
    private LocalDateTime arrivalDateTime;
    @NotEmpty(message = "Место вылета не может быть пустым")
    private Destination from;
    @NotEmpty(message = "Место прилета не может быть пустым")
    private Destination to;
    @NotNull(message = "Статус не может быть пустым")
    private FlightStatus flightStatus;
    @NotEmpty(message = "Нужно указать самолет")
    private Aircraft aircraft;
}
