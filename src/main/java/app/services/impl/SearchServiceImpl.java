package app.services.impl;

import app.entities.*;
import app.repositories.SearchRepository;
import app.services.interfaces.DestinationResourceService;
import app.services.interfaces.DestinationService;
import app.services.interfaces.SearchResultService;
import app.services.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;
    private final DestinationService destinationService;
    private final DestinationResourceService desResService;
    private final SearchResultService searchResultService;

    @Override
    public List<Search> getAll() {
        return searchRepository.findAll();
    }

    @Override
    public Optional<Search> getById(Long id) { return searchRepository.findById(id); }

    @Override
    public SearchResult createSearchResult(String cityFrom, String cityTo, LocalDate departureDate) {
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
        }
        // далее идёт составление маршрута согласно доступным аэропортам на пути из from в to
        // и указанному времени
        // getRoutes()
        // настройка маршрутов по дате-времени
        // создание Flight
        return new SearchResult();
    }

    @Override
    public Map<Integer, Map<DestinationResource, List<List<Route>>>> getRoutes(List<DestinationResource> resourceFrom,
                                                                               List<DestinationResource> resourceTo,
                                                                               LocalDate departureDate) {
        // DIRECT ROUTES
        Map<DestinationResource, List<Destination>> direct = null;
        if (!resourceFrom.isEmpty() && !resourceTo.isEmpty()) {
            direct =
                    getDestinationsByAvailableAirportCodes(resourceFrom, resourceTo);
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
        List<Route> routesByCode = new ArrayList<>();
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
        // TWO STOPS ROUTES
        Set<DestinationResource> resourceTwoStops = new HashSet<>();
        Map<DestinationResource, List<List<Route>>> twoStopsRoutesMap = null;
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
        // рейсы от второй до первой пересадки
        Map<DestinationResource, List<Destination>> firstStop =
                getDestinationsByAvailableAirportCodes(resourceFirstStopOfTwo, new ArrayList<>(twoStops.keySet()));
        // рейсы от первой до нулевой пересадки
        Map<DestinationResource, List<Destination>> initFromOfTwoStops =
                getDestinationsByAvailableAirportCodes(resourceFrom, new ArrayList<>(firstStop.keySet()));
        // одному аэропорту м.б. доступно несколько аэропортов в одном городе
        List<List<Route>> sharedListForRoutesByCodeOfSecondStopOfTwo = new ArrayList<>();
        twoStopsRoutesMap = new HashMap<>();
        for (DestinationResource zeroRes : initFromOfTwoStops.keySet()) {
            initFromOfTwoStops.get(zeroRes).forEach(zeroDest -> {
                for (DestinationResource firstRes : firstStop.keySet()) {
                    if (zeroRes.getAvailableAirportCodes().contains(firstRes.getBaseCode())
                            && !firstRes.getBaseCode().equals(zeroRes.getBaseCode())) {
                        for (DestinationResource secondRes : twoStops.keySet()) {
                            if (firstRes.getAvailableAirportCodes().contains(secondRes.getBaseCode())
                                    && !secondRes.getBaseCode().equals(firstRes.getBaseCode())
                                    && !secondRes.getBaseCode().equals(zeroRes.getBaseCode())) {
                                firstStop.get(firstRes).forEach(firstDest -> {
                                    twoStops.get(secondRes).forEach(secondDest -> {
                                        if (firstDest.getAirportCode().equals(secondRes.getBaseCode())) {
                                            Route route1 = Route.builder()
                                                    .from(constructDestinationByResource(zeroRes))
                                                    .departureDate(departureDate)
                                                    .to(constructDestinationByAnotherDestination(zeroDest))
                                                    .build();
                                            Route route2 = Route.builder()
                                                    .from(constructDestinationByAnotherDestination(zeroDest))
                                                    .departureDate(LocalDate.now())
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
                                });
                            }
                        }
                    }
                }
            });
            twoStopsRoutesMap.put(zeroRes, new ArrayList<>(sharedListForRoutesByCodeOfSecondStopOfTwo));
        }
        Map<Integer, Map<DestinationResource, List<List<Route>>>> allRoutes = new HashMap<>();
        allRoutes.put(0, directRoutesMap);
        allRoutes.put(1, oneStopRoutesMap);
        allRoutes.put(2, twoStopsRoutesMap);
        return allRoutes;
    }

    private Map<DestinationResource, List<Destination>> getDestinationsByAvailableAirportCodes
            (List<DestinationResource> fromList, List<DestinationResource> toList) {
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
