package app.entities.dtos;

import app.entities.Flight;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TicketDTO {
    private Long Id;
    @NotNull(message = "место не может быть пустым")
    private String seat;
    @NotNull(message = "цена не может быть пустой")
    private Long price;
    @NotNull(message = "перелет не может быть пустым")
    private Flight flight;
}
