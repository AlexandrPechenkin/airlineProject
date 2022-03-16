package app.entities.clients.admin;

import app.entities.clients.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class Admin extends User {
}
