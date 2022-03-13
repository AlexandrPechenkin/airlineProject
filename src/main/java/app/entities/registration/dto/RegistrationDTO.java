package app.entities.registration.dto;

import app.entities.passenger.Passenger;
import app.entities.ticket.Ticket;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class RegistrationDTO {

    private Long id;

    private Map<Passenger, List<Ticket>> passengerTickets;

    private String status;

    private LocalDateTime registrationDateTime;
}
