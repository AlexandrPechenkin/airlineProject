package app.entities;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс для связи между разными экземплярами Destination и для корректной работы класса Route, сервис
 * которого будет искать возможные/оптимальные маршруты из Destination from в Destination to
 * и обратно, если выбрано.
 *
 * Данные этого класса заполняются заранее, после чего происходит уже поиск доступных рейсов
 * согласно доступным аэропортам относительно каждого экземпляра Destination,
 * содержащего в себе уникальный код аэропорта, и по этому коду по данным из записей в БД об
 * этом классе определяются возможный маршрут.
 *
 * Дальнейшие пункты назначения настраиваются согласно доступным вариантам по времени при помощи сервиса Route.
 */
@Component
@Entity
@Table(name = "destination_resource")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DestinationResource {
    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Код аэропорта. Уникальный для всех стран и каждого города.
     * Base Airport Code для каждого экземпляра сущности {@link DestinationResource}.
     */
    @NonNull
    @Column(name = "base_code", unique = true)
    private String baseCode;
    /**
     * Название аэропорта. Уникально для каждого аэропорта.
     */
    @NonNull
    @Column(name = "airport_name", unique = true)
    private String airportName;
    /**
     * Название страны, в котором находится аэропорт.
     */
    @NonNull
    @Column(name = "country_name")
    private String countryName;
    /**
     * Код страны, в котором расположен аэропорт.
     */
    @Column(name = "country_code")
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;
    /**
     * Название города, в котором находится аэропорт и был выбран рейс from Destination.
     */
    @NonNull
    @Column(name = "city")
    private String city;
    /**
     * Список доступных аэропортов (по кодам аэропортов) относительно кода аэропорта, из которого совершается вылет.
     * Не должен иметь повторяющихся кодов внутри себя для одного и того же кода.
     */
    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> availableAirportCodes;
}
