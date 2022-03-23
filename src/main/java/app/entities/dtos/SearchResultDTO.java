package app.entities.dtos;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@ApiModel
public class SearchResultDTO {
    private long id;

    @Nullable
    private List<FlightDTO> departureFlights;

    @Nullable
    private List<FlightDTO> returnFlights;
}
