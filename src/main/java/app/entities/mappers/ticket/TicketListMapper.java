package app.entities.mappers.ticket;

import app.entities.Ticket;
import app.entities.dtos.TicketDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Маппер для списка билетов
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TicketListMapper {

    /**
     * Возвращает список DTO билетов по списку билетов
     * @param ticketList - список билетов
     * @return - список DTO билетов
     */
    List<TicketDTO> toDTOList(List<Ticket> ticketList);

    /**
     * Возвращает список билетов по списку DTO билетов
     * @param ticketDTOList - список DTO билетов
     * @return - список билетов
     */
    List<Ticket> toEntityList(List<TicketDTO> ticketDTOList);
}
