package app.entities.users.airlineManager.dto;

import app.entities.users.user.dto.UserDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class AirlineManagerDTO extends UserDTO {
}
