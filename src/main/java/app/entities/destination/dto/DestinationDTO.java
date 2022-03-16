package app.entities.destination.dto;

import app.util.CountryCode;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.TimeZone;

/**
 * DTO аэропорта
 */
@Data
@ApiModel
public class DestinationDTO {
    /**
     * Город, в котором находится аэропорт
     */
    @NotEmpty(message = "Поле city не должно быть пустым")
    private String city;
    /**
     * Код страны, в которой находится аэропорт
     */
//    @NotEmpty(message = "Поле countryCode не должно быть пустым")
    private CountryCode countryCode;
    /**
     * Название страны, в которой находится аэропорт
     */
    @NotEmpty(message = "Поле countryName не должно быть пустым")
    private String countryName;
    /**
     * Название аэропорта
     */
    @NotEmpty(message = "Поле airportName не должно быть пустым")
    private String airportName;
    /**
     * Код аэропорта
     */
    @NotEmpty(message = "Поле airportCode не должно быть пустым")
    private String airportCode;
    /**
     * Часовой пояс аэропорта
     */
//    @NotBlank(message = "Поле timeZone не должно быть пустым")
    private TimeZone timeZone;
}
