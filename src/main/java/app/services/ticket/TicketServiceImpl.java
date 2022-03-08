package app.services.ticket;

import app.entities.ticket.Ticket;
import app.repositories.ticket.TicketRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * метод отображения всех билетов
     */
    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    /**
     * метод создания билетов
     */
    @Transactional
    @Override
    public void createTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    /**
     * метод удаления билета
     */
    @Transactional
    @Override
    public void removeTicket(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    /**
     * поиск билета по id
     */
    @Transactional
    @Override
    public Ticket getTicketByID(Long id) {
        return ticketRepository.getById(id);
    }


    /**
     * поиск билета с пересадками по маршруту дате вылета
     * @param route маршрут
     * @param departureDate дата вылета
     * @return
     */
    @Transactional
    @Override
    public List<Ticket> findTickets(String route, LocalDate departureDate) {
        List<Ticket> ticketList = ticketRepository.findTickets(route, departureDate);
        return ticketList;
    }
}
