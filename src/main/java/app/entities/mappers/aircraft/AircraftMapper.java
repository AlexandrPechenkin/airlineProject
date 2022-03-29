package app.entities.mappers.aircraft;

import app.entities.Aircraft;
import app.entities.dtos.AircraftDTO;
import app.entities.mappers.category.CategoryMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Маппер MapStruct для Воздушного судна
 * @author Александр Данилов
 * @version 0.1
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CategoryMapper.class})
public interface AircraftMapper {
    /**
     * Метод сопоставляет Aircraft c AircraftDTO
     * @param aircraft - Воздушное судно
     * @return - {@link AircraftDTO}
     */
    AircraftDTO toDto(Aircraft aircraft);

    /**
     * Метод сопоставляет AircraftDTO c Aircraft
     * @param aircraftDTO - Поля воздушного судна которые отдаются наружу
     * @return - {@link Aircraft}
     */
    Aircraft toEntity(AircraftDTO aircraftDTO);
}
