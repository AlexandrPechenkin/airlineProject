package app.entities.dtos;

import app.entities.Flight;
import app.entities.Seat;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TicketDTO {
    private Long Id;
    @NotNull(message = "место не может быть пустым")
    private Seat seat;
    @NotNull(message = "номер бронирования не может быть пустой")
    private Long holdNumber;
    @NotNull(message = "цена не может быть пустой")
    private Long price;
    @NotNull(message = "перелет не может быть пустым")
    private Flight flight;
}
