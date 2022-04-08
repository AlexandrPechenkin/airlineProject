package app.entities.dtos;

import app.entities.Category;
import app.entities.Search;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class RouteDTO {
    private Long id;
    @NotNull(message = "Дата вылета не может быть пустой")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate departureDate;
    @NotNull(message = "Дата вылета обратно не может быть пустой")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate departureDateOfReturn;
    @NotNull(message = "Количество сидений не может быть пустым")
    private int numberOfSeats;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "search_id", nullable = false)
    private Search search;
    /**
     * Destination from
     */
//    @NonNull
//    private Destination from;
    @NotNull(message = "Вылет не может быть пустым")
    private String from;
    /**
     * Destination to
     */
    @NotNull(message = "Место прилета не может быть пустым")
//    @NonNull
//    private Destination to;
    private String to;
}
