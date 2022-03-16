package app.mappers.seat;

import app.entities.seat.Seat;
import app.entities.seat.dto.SeatDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SeatMapper.class})

public interface SeatMapper {
    /**
     * Метод сопоставляет Seat c SeatDTO
     *
     * @param seat - категория
     */
    SeatDTO toDto(Seat seat);

    /**
     * Метод сопоставляет SeatDTO c Seat
     *
     * @param seatDTO - Поля места которые отдаются наружу
     */
    Seat toEntity(SeatDTO seatDTO);
}


