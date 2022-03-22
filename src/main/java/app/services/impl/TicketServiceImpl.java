package app.services.impl;

import app.entities.Ticket;
import app.repositories.TicketRepository;
import app.services.interfaces.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

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
    @Override
    public Ticket createOrUpdateTicket(Ticket ticket) {
        return  ticketRepository.save(ticket);
    }

    /**
     * метод удаления билета
     */
    @Override
    public void removeTicket(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    /**
     * поиск билета по id
     */
    @Override
    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }
}
