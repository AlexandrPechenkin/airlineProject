package app.entities.clients.airlineManager;

import app.entities.clients.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "Airline_manager")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class AirlineManager extends User {

}
