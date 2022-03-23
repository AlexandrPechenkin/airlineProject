package app.entities.dtos;

import app.entities.Flight;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

/**
 * Data Transfer Object - передающий данные из Seat наружу.
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */
@Data
public class SeatDTO {
    private Long id;

    @NotEmpty(message = "Поле seatNumber не должно быть пустым")
    private String seatNumber;

    @NonNull
    private Integer fare;

    @NonNull
    private Boolean isRegistered;

    @NonNull
    private Boolean isSold;

    private Flight flight;
}
