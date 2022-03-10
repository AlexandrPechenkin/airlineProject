package app.services.search;

import app.entities.route.Route;
import app.entities.ticket.Ticket;
import app.repositories.search.SearchRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    /**
     * поиск билета с пересадками по маршруту дате вылета
     *
     * @param from место вылета
     * @param to   место прилета
     * @param departureDate   дата вылета
     * @return
     */
    @Transactional
    @Override
    public List<Route> findTickets(String from, String to, LocalDate departureDate) {
        List<Route> ticketList = searchRepository.findTickets(from, to, departureDate);
        return ticketList;
    }
}
