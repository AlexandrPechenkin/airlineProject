package app.entities.mappers.category;


import app.entities.Category;
import app.entities.dtos.CategoryDTO;
import app.entities.mappers.seat.SeatMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Маппер MapStruct для Категории
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SeatMapper.class})
public interface CategoryMapper {
    /**
     * Метод сопоставляет Category c CategoryDTO
     *
     * @param category - категория
     */
    CategoryDTO toDto(Category category);

    /**
     * Метод сопоставляет CategoryDTO c Category
     *
     * @param categoryDTO - Поля категории которые отдаются наружу
     */
    Category toEntity(CategoryDTO categoryDTO);
}
