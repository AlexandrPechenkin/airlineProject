package app.entities.clients.passenger;


import app.entities.clients.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Класс Passenger отвечает за состояние пассажира и связь с паспортом.
 * @author Александр Данилов
 * @version 0.1
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "passengers")
public class Passenger extends User {

    /**
     * Имя пассажира
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * Фамилия пассажира
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * Отчество пассажира
     */
    @Column(name = "middle_name")
    private String middleName;
    /**
     * День рождения пассажира
     */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Связь с Passport
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id")
    private Passport passport;
}
