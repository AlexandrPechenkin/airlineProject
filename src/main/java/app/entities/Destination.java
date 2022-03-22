package app.entities;

import lombok.*;

import javax.persistence.*;
import java.util.TimeZone;

/**
 * Аэропорт (пункт вылета и/или прилёта)
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "destination")
public class Destination {
    /**
     * ID аэропорта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Город, в котором находится аэропорт
     */
    @Column(name = "city")
    private String city;
    /**
     * Код страны, в которой находится аэропорт
     */
    @Column(name = "country_code")
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;
    /**
     * Название страны, в которой находится аэропорт
     */
    @Column(name = "country_name")
    private String countryName;
    /**
     * Название аэропорта
     */
    @Column(name = "airport_name")
    private String airportName;
    /**
     * Код аэропорта
     */
    @Column(name = "airport_code")
    private String airportCode;
    /**
     * Часовой пояс аэропорта
     */
    @Column(name = "time_zone")
    private TimeZone timeZone;
}
