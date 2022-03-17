package app.entities.seat.dto;

import app.entities.category.Category;
import app.entities.flight.Flight;
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

    private Category category;

    private Flight flight;
}
