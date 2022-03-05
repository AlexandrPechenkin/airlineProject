package app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ticket")
@Component
public class Ticket {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //место вылета
    @NonNull
    private String origin;

    //место прилета
    @NonNull
    private String destination;

    //дата вылета
    @NonNull
    private String departureDate;

    //время вылета
    @NonNull
    private String departureTime;

    //дата прилета
    @NonNull
    private String arrivalDate;

    //время прилета
    @NonNull
    private String arrivalTime;

    //стоимость билета
    @NonNull
    private Long ticketPrice;

    //номер кресла
    @NonNull
    private String seatNumber;

}
