package app.mappers.ticket;

import app.entities.ticket.DTO.TicketDTO;
import app.entities.ticket.Ticket;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {app.mappers.ticket.TicketMapper.class})
public interface TicketMapper {

    /**
     * Метод сопоставляет Flight c FlightDTO
     *
     * @param ticket - билет
     */
    TicketDTO toDTO(Ticket ticket);

    /**
     * Метод сопоставляет FlightDTO c Flight
     *
     * @param ticketDTO - Поля билета, которые отдаются наружу
     */
    Ticket toEntity(TicketDTO ticketDTO);

}
