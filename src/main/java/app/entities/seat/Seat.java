package app.entities.seat;

import app.entities.flight.Flight;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "Seat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Component
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "fare")
    private Integer fare;

    @ManyToOne
    private Flight flight_id;

    @Column(name = "is_registered")
    private Boolean isRegistered;

    @Column(name = "is_sold")
    private Boolean isSold;
}
