package app.services;

import app.entities.Ticket;
import app.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    //метод отображения всех билетов
    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }


    //метод создания билетов
    @Transactional
    @Override
    public void createTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }


    //метод удаления билета
    @Transactional
    @Override
    public void removeTicket(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    //поиск билета по id
    @Transactional
    @Override
    public Ticket getTicketByID(Long id) {
        return ticketRepository.getById(id);
    }

    //поиск билета по месту вылета
    @Transactional
    @Override
    public List<Ticket> findTicketsByOrigin(String origin) {
        return ticketRepository.findTicketsByOrigin(origin);
    }


    //поиск билета по месту прилета
    @Transactional
    @Override
    public List<Ticket> findTicketsByDestination(String destination) {
        return ticketRepository.findTicketsByDestination(destination);
    }


    //поиск билета по месту вылета и месту прилета
    @Transactional
    @Override
    public List<Ticket> findTicketsByOriginAndDestination(String origin, String destination) {
        return ticketRepository.findTicketsByOriginAndDestination(origin, destination);
    }

    @Transactional
    @Override
    public List<Ticket> findTickets(String origin, String destination, GregorianCalendar departureDate) {
        return ticketRepository.findTickets(origin, destination, departureDate);
    }
}
