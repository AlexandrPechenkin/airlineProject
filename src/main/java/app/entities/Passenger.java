package app.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Класс Passenger отвечает за состояние пассажира и связь с паспортом.
 * @author Александр Данилов
 * @version 0.1
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "Passenger")
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
