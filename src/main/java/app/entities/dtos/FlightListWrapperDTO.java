package app.entities.dtos;

import app.entities.Flight;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@ApiModel
@Jacksonized
public class FlightListWrapperDTO {
    private long id;
    private List<Flight> allFlightsInOneFLight;
}
