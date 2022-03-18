package app.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

/**
 * Класс, описывающий менеджера авиакомпании. Отвечает за управление авиапарком, рейсами, билетами.
 */
@Entity(name = "AirlineManager")
@Table(name = "Airline_manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AirlineManager extends User {

    /**
     * Название авиапарка, которым заведует менеджер.
     */
    @Column(name = "fleetName")
    private String parkName;
}
