package app.entities.registration.dto;

import app.entities.booking.Booking;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationDTO {

    private Long id;

    private Booking booking;

    private LocalDateTime registrationDateTime;
}
