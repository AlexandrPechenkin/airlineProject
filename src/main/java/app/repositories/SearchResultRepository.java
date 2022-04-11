package app.repositories;

import app.entities.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchResultRepository extends JpaRepository<SearchResult, Long> {
//    MultiValueMap<DestinationResource, List<Flight>> findByDepartFlights(
//            Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> map
//    );
//    @Query(value = "SELECT s FROM SearchResult s join s.departFlights fl where ?1 in value(fl)")
//    Optional<MultiValueMap<DestinationResource, List<Flight>>> findByDepartFlights(Integer numberOfStops);
}
