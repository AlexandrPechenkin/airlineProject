package app.repositories.search;

import app.entities.search.Search;
import org.springframework.data.jpa.repository.JpaRepository;




public interface SearchRepository extends JpaRepository<Search, Long> {

}
