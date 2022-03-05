package app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.GregorianCalendar;

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
    private GregorianCalendar departureDate;

    //дата прилета
    @NonNull
    private GregorianCalendar arrivalDate;

    //стоимость билета
    @NonNull
    private Long ticketPrice;

    //номер кресла
    @NonNull
    private String seatNumber;

}
