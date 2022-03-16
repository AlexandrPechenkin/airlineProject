package app.entities.flight.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FlightDTO {
    private long id;

    @NotEmpty
    private String destinationFrom;

    @NotEmpty
    private String destinationTo;
}
