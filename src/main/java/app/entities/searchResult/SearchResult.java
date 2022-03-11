package app.entities.searchResult;

import app.entities.flight.Flight;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Search_result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Component
public class SearchResult {

    /**
     * уникальный идентификатор результата поиска рейсов
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * список рейсов from
     */
    @NonNull
    @OneToMany
    @JoinColumn(name = "search_result_depart")
    private List<Flight> depart;

    /**
     * список рейсов to
     */
    @Nullable
    @OneToMany
    @JoinColumn(name = "search_result_arrive")
    private List<Flight> arrive;
}
