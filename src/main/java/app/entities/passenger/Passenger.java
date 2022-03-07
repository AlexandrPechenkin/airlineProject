package app.entities.passenger;


import lombok.*;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passengers")
public class Passenger {
    /**
     * Уникальный идентификатор пассажира
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Электронная почта пассажира
     */
    @Column(name = "email")
    private String email;
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
     * Связь с passportPassenger
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id")
    private Passport passport;
}
