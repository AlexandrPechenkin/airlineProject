package app.services.impl;

import app.entities.CountryCode;
import app.entities.Destination;
import app.entities.Flight;
import app.entities.SearchResult;
import app.repositories.SearchResultRepository;
import app.services.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class SearchResultServiceImpl implements SearchResultService {
    SearchResultRepository searchResultRepository;
    DestinationService destinationService;
    FlightService flightService;
    SearchService searchService;
    TicketService ticketService;

    @Override
    public SearchResult createOrUpdateSearchResult(SearchResult searchResult) {
        return null;
    }

    public SearchResult getSearchResult(String cityFrom, String cityTo) {
        Destination nizhnyNovgorod = destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .city("Nizhny Novgorod")
                        .airportName("Strigino")
                        .airportCode("GOJ")
                        .timeZone(TimeZone.getTimeZone("Europe/Nizhny Novgorod"))
                        .build()
        );
        Destination moscow = destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .city("Moscow")
                        .airportName("Domodedovo")
                        .airportCode("DME")
                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                        .build()
        );
        Destination novosibirsk = destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .city("Novosibirsk")
                        .airportName("Tolmachevo")
                        .airportCode("OVB")
                        .timeZone(TimeZone.getTimeZone("Europe/Novosibirsk"))
                        .build()
        );
        Destination vladivostok = destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .city("Vladivostok")
                        .airportName("Knevichi")
                        .airportCode("VVO")
                        .timeZone(TimeZone.getTimeZone("Europe/Vladivostok"))
                        .build()
        );

        return new SearchResult();
    }

    @Override
    public Optional<SearchResult> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<SearchResult> findAll() {
        return null;
    }

    @Override
    public void deleteSearchResultById(long id) {

    }
}
