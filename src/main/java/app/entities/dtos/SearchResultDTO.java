package app.entities.dtos;

import app.entities.FlightContainer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

import java.util.Map;

@Data
@ApiModel
@Jacksonized
public class SearchResultDTO {
    private long id;

    @Nullable
    private Map<Integer, FlightContainer> departFlights;

    @Nullable
    private Map<Integer, FlightContainer> returnFlights;

    @Nullable
    private String message;
}
