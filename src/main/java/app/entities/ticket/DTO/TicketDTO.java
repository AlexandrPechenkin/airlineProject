package app.entities.ticket.DTO;

import app.entities.flight.Flight;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Data
public class TicketDTO {
    private Long Id;
    @PastOrPresent
    @NotNull(message = "место не может быть пустым")
    private String seat;
    @PastOrPresent
    @NotNull(message = "цена не может быть пустой")
    private Long price;
    @PastOrPresent
    @NotNull(message = "перелет не может быть пустым")
    private Flight flight;
}
