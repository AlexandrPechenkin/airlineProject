package app.entities.users.passenger;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Класс Passport отвечает за состояние паспорта пассажира.
 * @author Александр Данилов
 * @version 0.1
 */
@Entity(name = "Passport")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passenger_passport")
public class Passport {
    /**
     * Уникальный идентификатор пассажира
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пассажира в паспорте
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия пассажира в паспорте
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Отчество пассажира в паспорте
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Пол пассажира в паспорте
     */
    @Column(name = "gender")
    private String gender;

    /**
     * Место рождения пассажира в паспорте
     */
    @Column(name = "birthplace")
    private String birthplace;

    /**
     * Прописка пассажира в паспорте
     */
    @Column(name = "residence_registration")
    private String residenceRegistration;

    /**
     * Дата рождения пассажира в паспорте
     */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    /**
     * Серия и номер паспорта пассажира
     */
    @Column(name = "series_and_number")
    private String seriesAndNumber;
}
