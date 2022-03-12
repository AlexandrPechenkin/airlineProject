package app.entities.booking.dto;

import app.entities.flight.Flight;
import app.entities.passenger.Passenger;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class BookingDTO {

    private Long id;

    private Map<Passenger, List<Flight>> passengerFlightsDepart;

    private Map<Passenger, List<Flight>> passengerFlightsReturn;

    private String paymentMethod;

    private Boolean isSold;

    private String status = "IN_PROGRESS";

    private LocalDateTime initialBookingDateTime;
}
