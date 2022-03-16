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
    private String seatNumber;  //номер сиденья

    @NonNull
    private Integer fare; //плата за проезд

    @NonNull
    private Boolean isRegistered;  //регистрация

    @NonNull
    private Boolean isSold;     //продано

    private Category category;

    private Flight flight;
}
