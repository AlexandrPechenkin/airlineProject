package app.entities.mappers.search;


import app.entities.Search;
import app.entities.dtos.SearchDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SearchMapper.class})
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
