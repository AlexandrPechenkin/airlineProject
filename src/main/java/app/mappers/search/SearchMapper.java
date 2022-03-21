package app.mappers.search;


import app.entities.search.Search;
import app.entities.search.dto.SearchDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {app.mappers.search.SearchMapper.class})
public interface SearchMapper {

    /**
     * Метод сопоставляет Search c SearchDTO
     *
     * @param search - поиск
     */
    SearchDTO toDTO(Search search);

    /**
     * Метод сопоставляет SearchDTO c Search
     *
     * @param searchDTO - Поля билета, которые отдаются наружу
     */
    Search toEntity(SearchDTO searchDTO);
}
