package app.entities.users.airlineManager;

import app.entities.users.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity(name = "AirlineManager")
@Table(name = "Airline_manager")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class AirlineManager extends User {

}
