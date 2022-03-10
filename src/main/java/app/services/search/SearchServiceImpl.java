package app.services.search;

import app.entities.ticket.Ticket;
import app.repositories.search.SearchRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    private final SearchRepository searchRepository;

    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
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
        List<Ticket> ticketList = searchRepository.findTickets(route, departureDate);
        return ticketList;
    }
}
