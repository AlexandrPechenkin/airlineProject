package app.entities.dtos;

import app.entities.Passenger;
import app.entities.Ticket;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * DTO для класса Booking.
 */
@Data
@ApiModel
public class BookingDTO {

    private long id;

    @NotEmpty(message = "Пожалуйста, укажите билет.")
    private Ticket departTicket;

    private Passenger passenger;

    private String paymentMethod;

    private String category;

    private Long holdNumber;

    private String status;

    private LocalDateTime initialBookingDateTime;
}
