package app.services.impl;

import app.entities.*;
import app.repositories.SearchRepository;
import app.services.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;
    private final DestinationResourceService desResService;
    private final SearchResultService searchResultService;
    private final FlightService flightService;
    private final FlightContainerService flightContainerService;
    private final FlightListWrapperService flightListWrapperService;

    @Override
    public List<Search> getAll() {
        return searchRepository.findAll();
    }

    @Override
    public Optional<Search> getById(Long id) { return searchRepository.findById(id); }

    @Override
    public SearchResult getSearchResultByCitiesAndLocalDates(String cityFrom, String cityTo,
                                                             LocalDate departureDate, LocalDate returnDate) {
        // существуют ли вообще аэропорты в указанных городах
        List<DestinationResource> resourceFrom = desResService.findByCity(cityFrom);
        List<DestinationResource> resourceTo = desResService.findByCity(cityTo);
        if (resourceFrom.isEmpty() && resourceTo.isEmpty()) {
            return searchResultService.createOrUpdateSearchResult(
                    SearchResult.builder()
                            .message("К сожалению, рейсов 'из' и 'в' указанные места нет. " +
                                    "Пожалуйста, выберите другие пункты назначения.")
                            .departFlights(null)
                            .returnFlights(null)
                            .build());
        } else if (resourceFrom.isEmpty()) {
            return searchResultService.createOrUpdateSearchResult(
                    SearchResult.builder()
                            .message("К сожалению, рейсов из указанного места нет. " +
                                    "Пожалуйста, выберите другой пункт назначения.")
                            .departFlights(null)
                            .returnFlights(null)
                    .build());
        } else if (resourceTo.isEmpty()) {
            return searchResultService.createOrUpdateSearchResult(
                    SearchResult.builder()
                            .message("К сожалению, рейсов в указанное место нет. " +
                                    "Пожалуйста, выберите другой пункт назначения.")
                            .departFlights(null)
                            .returnFlights(null)
                            .build());
        } else {
            SearchResult searchResult = new SearchResult();
            searchResult.setDepartFlights(getAvailableFlights(resourceFrom, resourceTo, departureDate));
            if (returnDate != null) {
                searchResult.setReturnFlights(getAvailableFlights(resourceTo, resourceFrom, returnDate));
            }
            return searchResultService.createOrUpdateSearchResult(searchResult);
        }
    }

    private Map<Integer, FlightContainer> getAvailableFlights(List<DestinationResource> resourceFrom,
                                                              List<DestinationResource> resourceTo,
                                                              LocalDate date) {
        // поиск возможных комбинаций маршрутов из from в to
        Map<Integer, MultiValueMap<DestinationResource, List<Route>>> routes =
                getRoutes(resourceFrom, resourceTo);
        // поиск доступных рейсов по дате-времени среди тех комбинаций маршрутов выше, что нашлись
        Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> flights = getFlights(routes, date);
        Map<Integer, FlightContainer> map = new HashMap<>();
        SearchResult searchResult = new SearchResult();
        flights.forEach((numberOfSteps, multiValueMap) -> {
            multiValueMap.keySet().forEach(res -> {
                List<FlightListWrapper> wrapList = new ArrayList<>();
                flights.get(numberOfSteps).get(res).forEach(flightList -> {
                    wrapList.add(flightListWrapperService.createOrUpdateFlightListWrapper(
                            FlightListWrapper.builder()
                                    .allFlightsFromToCities(flightList)
                                    .build()));
                });
                FlightContainer flightContainer = FlightContainer.builder()
                        .numberOfSteps(numberOfSteps)
                        .destinationResource(res)
                        .flights(wrapList)
//                        .searchResult(searchResult)
                        .build();
                map.put(numberOfSteps, flightContainer);
                flightContainerService.createOrUpdateFlightContainer(flightContainer);
            });
        });
        return map;
    }

    private Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> getFlights(
            Map<Integer, MultiValueMap<DestinationResource, List<Route>>> resourceFlight, LocalDate departureDate) {
        Map<Integer, Map<DestinationResource, List<List<Flight>>>> flights = new HashMap<>();
        Map<DestinationResource, List<List<Flight>>> flightsMap = new HashMap<>();
        List<List<Flight>> listFlight = new ArrayList<>();
        List<Flight> flight = new ArrayList<>();
        if (!resourceFlight.isEmpty()) {
            resourceFlight.forEach((numberOfStops, multiRoutesValueMap) -> {
                multiRoutesValueMap.forEach((destRes, listOfRoutesList) -> {
                    listOfRoutesList.forEach(routesListForEachDestRes -> {
                        routesListForEachDestRes.forEach(route -> {
                            List<Flight> temp = flightService.findFlights(
                                    route.getFrom().getCity(),
                                    route.getTo().getCity(),
                                    departureDate);
                            if (!temp.isEmpty()) {
                                Flight f = temp.get(0);
                                flight.add(f);
                            }
                        });
                        if (flight.size() == routesListForEachDestRes.size()) {
                            listFlight.add(new ArrayList<>(flight));
                            flightsMap.put(destRes, new ArrayList<>(listFlight));
                            flight.clear();
                        }
                    });
                });
                flights.put(numberOfStops, new HashMap<>(flightsMap));
                flightsMap.clear();
                listFlight.clear();
            });
        }
        Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> mapMultiValueFlightsMap = new HashMap<>();
        flights.forEach((numberOfStops, map) -> {
            map.keySet().forEach(destinationResource -> {
                MultiValueMap<DestinationResource, List<Flight>> multiValueTemp = new MultiValueMapAdapter<>(map);
                mapMultiValueFlightsMap.put(numberOfStops, multiValueTemp);
            });
        });
        return mapMultiValueFlightsMap;
    }

    private Map<Integer, MultiValueMap<DestinationResource, List<Route>>> getRoutes(List<DestinationResource> resourceFrom,
                                                                               List<DestinationResource> resourceTo) {
        // DIRECT ROUTES
        Map<DestinationResource, List<Destination>> direct = null;
        if (!resourceFrom.isEmpty() && !resourceTo.isEmpty()) {
            direct = getDestinationsByAvailableAirportCodes(resourceFrom, resourceTo);
        }
        Map<DestinationResource, List<List<Route>>> directRoutesMap = new HashMap<>();
        List<List<Route>> directRoutes = new ArrayList<>();
        List<Route> routeListDirect = new ArrayList<>();
        // построение маршрутов
        // прямой рейс, ЕСЛИ нашлись подходящие коды аэропортов
        if (direct != null && !direct.isEmpty()) {
            for (DestinationResource res : direct.keySet()) {
                direct.get(res).forEach(destination -> {
                    Route route = Route.builder()
                            .from(constructDestinationByResource(res))
                            .to(destination)
                            .build();
                    routeListDirect.add(route);
                    directRoutes.add(new ArrayList<>(routeListDirect));
                });
                directRoutesMap.put(res, new ArrayList<>(directRoutes));
                directRoutes.clear();
                routeListDirect.clear();
            }
        }
        MultiValueMap<DestinationResource, List<Route>> directRoutesMultiMap = new MultiValueMapAdapter<>(
                directRoutesMap);
        // ONE STOP ROUTES
        List<DestinationResource> resourceFirstStopOfOne = new ArrayList<>();
        // Мапа, которая ставит в соответствие каждому коду аэропорта из указанного города from все доступные
        // рейсы в город to (во все доступные аэропорты города to)
        Map<DestinationResource, List<List<Route>>> oneStopRoutesMap = null;
        resourceFrom.forEach(res -> {
            res.getAvailableAirportCodes()
                    .forEach(code -> {
                        DestinationResource directRes = desResService.findByBaseAirportCode(code);
                        if (Objects.nonNull(directRes)) {
                            resourceFirstStopOfOne.add(directRes);
                        }
                    });
        });
        // ищем общие коды аэропортов среди доступных кодов аэропортов от доступных кодов базовых/начальных
        // кодов DestinationResource
        Map<DestinationResource, List<Destination>> oneStop =
                getDestinationsByAvailableAirportCodes(resourceFirstStopOfOne, resourceTo);
        // находим все начальные коды каждого DestinationResource from из List<DestinationResource> fromList,
        // удовлетворяющих условию:
        // from.availableAirportCodes.contains(any of to.baseCode by List<DestinationResource> toList)
        Map<DestinationResource, List<Destination>> resourceFirstStopOfOneResidueAfterSearch =
                getDestinationsByAvailableAirportCodes(resourceFrom, new ArrayList<>(oneStop.keySet()));
        oneStopRoutesMap = new HashMap<>();
        // одному аэропорту м.б. доступно несколько аэропортов в одном городе
        List<List<Route>> sharedListForRoutesByCode = new ArrayList<>();
        // список маршрутов для одного кода аэропорта из from и ОДНОГО кода аэропорта из to
        if (!oneStop.isEmpty()) {
            for (DestinationResource res : resourceFirstStopOfOneResidueAfterSearch.keySet()) {
                for (DestinationResource resOneStop : oneStop.keySet()) {
                    if (res.getAvailableAirportCodes().contains(resOneStop.getBaseCode())
                            && !resOneStop.getBaseCode().equals(res.getBaseCode())) {
                        resourceFirstStopOfOneResidueAfterSearch.get(res).forEach(firstStopOfOneDest -> {
                            oneStop.get(resOneStop).forEach(toDest -> {
                                if (firstStopOfOneDest.getAirportCode().equals(resOneStop.getBaseCode())) {
                                    Route route1 = Route.builder()
                                            .from(constructDestinationByResource(res))
                                            .to(constructDestinationByAnotherDestination(firstStopOfOneDest))
                                            .build();
                                    Route route2 = Route.builder()
                                            .from(constructDestinationByAnotherDestination(firstStopOfOneDest))
                                            .to(constructDestinationByAnotherDestination(toDest))
                                            .build();
                                    sharedListForRoutesByCode.add(new ArrayList<>() {{
                                        add(route1);
                                        add(route2);
                                    }});
                                }
                            });
                        });
                    }
                }
                oneStopRoutesMap.put(res, new ArrayList<>(sharedListForRoutesByCode));
            }
        }
        MultiValueMap<DestinationResource, List<Route>> oneStopRoutesMultiMap = new MultiValueMapAdapter<>(
                oneStopRoutesMap);
        // TWO STOPS ROUTES
        Set<DestinationResource> resourceTwoStops = new HashSet<>();
        Map<DestinationResource, List<List<Route>>> twoStopsRoutesMap = new HashMap<>();
        // убираем все рейсы с одной пересадкой (прямые рейсы уже убраны)
        List<DestinationResource> resourceFirstStopOfTwo = new ArrayList<>();
        resourceFrom.forEach(res -> {
            res.getAvailableAirportCodes().forEach(code -> {
                DestinationResource firstStop = desResService.findByBaseAirportCode(code);
                if (Objects.nonNull(firstStop)) {
                    resourceFirstStopOfTwo.add(firstStop);
                    firstStop.getAvailableAirportCodes().forEach(availableCodeFirst -> {
                        DestinationResource secondStop = desResService.findByBaseAirportCode(availableCodeFirst);
                        if (Objects.nonNull(secondStop)) {
                            resourceTwoStops.add(secondStop);
                        }
                    });
                }
            });
        });
        Map<DestinationResource, List<Destination>> twoStops =
                getDestinationsByAvailableAirportCodes(new ArrayList<>(resourceTwoStops), resourceTo);
        if (!twoStops.isEmpty()) {
            // рейсы от второй до первой пересадки
            Map<DestinationResource, List<Destination>> firstStop =
                    getDestinationsByAvailableAirportCodes(resourceFirstStopOfTwo, new ArrayList<>(twoStops.keySet()));
            // рейсы от первой до нулевой пересадки
            Map<DestinationResource, List<Destination>> initFromOfTwoStops =
                    getDestinationsByAvailableAirportCodes(resourceFrom, new ArrayList<>(firstStop.keySet()));
            // одному аэропорту м.б. доступно несколько аэропортов в одном городе
            List<List<Route>> sharedListForRoutesByCodeOfSecondStopOfTwo = new ArrayList<>();
//            twoStopsRoutesMap = new HashMap<>();
            for (DestinationResource zeroRes : initFromOfTwoStops.keySet()) {
                initFromOfTwoStops.get(zeroRes).forEach(zeroDest -> {
                    for (DestinationResource firstRes : firstStop.keySet()) {
                        if (zeroDest.getAirportCode().equals(firstRes.getBaseCode())) {
                            firstStop.get(firstRes).forEach(firstDest -> {
                                for (DestinationResource secondRes : twoStops.keySet()) {
                                    if (firstRes.getAvailableAirportCodes().contains(secondRes.getBaseCode())
                                            && !secondRes.getBaseCode().equals(firstRes.getBaseCode())
                                            && !secondRes.getBaseCode().equals(zeroRes.getBaseCode())) {
                                        twoStops.get(secondRes).forEach(secondDest -> {
                                            if (firstDest.getAirportCode().equals(secondRes.getBaseCode())) {
                                                Route route1 = Route.builder()
                                                        .from(constructDestinationByResource(zeroRes))
//                                                        .departureDate(departureDate)
                                                        .to(constructDestinationByAnotherDestination(zeroDest))
                                                        .build();
                                                Route route2 = Route.builder()
                                                        .from(constructDestinationByAnotherDestination(zeroDest))
//                                                        .departureDate(LocalDate.now())
                                                        .to(constructDestinationByAnotherDestination(firstDest))
                                                        .build();
                                                Route route3 = Route.builder()
                                                        .from(constructDestinationByAnotherDestination(firstDest))
                                                        .to(constructDestinationByAnotherDestination(secondDest))
                                                        .build();
                                                sharedListForRoutesByCodeOfSecondStopOfTwo.add(new ArrayList<>() {{
                                                    add(route1);
                                                    add(route2);
                                                    add(route3);
                                                }});
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                twoStopsRoutesMap.put(zeroRes, new ArrayList<>(sharedListForRoutesByCodeOfSecondStopOfTwo));
            }
        }
        MultiValueMap<DestinationResource, List<Route>> twoStopsRoutesMultiMap = new MultiValueMapAdapter<>(
                twoStopsRoutesMap);
        Map<Integer, MultiValueMap<DestinationResource, List<Route>>> all = new HashMap<>();
        all.put(0, directRoutesMultiMap);
        all.put(1, oneStopRoutesMultiMap);
        all.put(2, twoStopsRoutesMultiMap);

        return all;
    }

    private Map<DestinationResource, List<Destination>> getDestinationsByAvailableAirportCodes
            (List<DestinationResource> fromList, List<DestinationResource> toList) {
        if (fromList.isEmpty() || toList.isEmpty()) {
            return new HashMap<>();
        }
        // Map списков доступных мест прибытия относительно каждого DestinationResource from из fromList
        Map<DestinationResource, List<Destination>> availableDestinations = new HashMap<>();
        // Map всех DestinationResource to из List<DestinationResource> toList с указанием базового кода аэропорта из to
        Map<String, DestinationResource> toDestResMap = new HashMap<>();
        // список всех уникальных базовых кодов аэропортов из списка toList
        Set<String> baseCodesByToList = new HashSet<>();
        // обычный перевод из List<DestinationResource> toList
        // в Map<String, DestinationResource> === Map<to.baseCode, to> для более удобного итерирования далее в методе
        toList.forEach(res -> {
            baseCodesByToList.add(res.getBaseCode());
            toDestResMap.put(res.getBaseCode(), res);
        });
        // инициализация списка доступных мест прибытия относительно каждого DestinationResource from из fromList
        List<Destination> destinations = new ArrayList<>();
        // для каждого DestinationResource мы сравниваем его availableAirportCodes со всеми уникальными
        // кодами среди всех DestinationResource из List<DestinationResource> toList
        // если нашлись общие коды, то добавляем к списку доступных мест прибытий Destination с совпавшим кодом.
        fromList.forEach(res -> {
            Set<String> availableCodes = new HashSet<>(res.getAvailableAirportCodes());
            if (availableCodes.retainAll(baseCodesByToList) && !availableCodes.isEmpty() || availableCodes.containsAll(baseCodesByToList)) {
                availableCodes.forEach(availableCode -> {
                    DestinationResource to = toDestResMap.get(availableCode);
                    destinations.add(Destination.builder()
                        .countryCode(to.getCountryCode())
                        .countryName(to.getCountryName())
                        .city(to.getCity())
                        .airportCode(to.getBaseCode())
                        .airportName(to.getAirportName()).build());
                    }
                );
                availableDestinations.put(res, new ArrayList<>(destinations));
                destinations.clear();
            }
        });
        return availableDestinations;
    }

    // Служебные методы для конструирования Destination из DestinationResource и других Destination
    private Destination constructDestinationByResource(DestinationResource res) {
        return Destination.builder()
                .countryCode(res.getCountryCode())
                .countryName(res.getCountryName())
                .city(res.getCity())
                .airportCode(res.getBaseCode())
                .airportName(res.getAirportName())
                .build();
    }

    private Destination constructDestinationByAnotherDestination(Destination des) {
        return Destination.builder()
                .countryCode(des.getCountryCode())
                .countryName(des.getCountryName())
                .city(des.getCity())
                .airportCode(des.getAirportCode())
                .airportName(des.getAirportName())
                .build();
    }
}
