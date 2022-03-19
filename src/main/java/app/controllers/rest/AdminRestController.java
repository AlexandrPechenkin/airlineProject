package app.controllers.rest;

import app.entities.Admin;
import app.entities.dtos.AdminDTO;
import app.entities.mappers.admin.AdminMapper;
import app.services.interfaces.AdminService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RestController для управления записями о классе {@link Admin} в БД.
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "AdminRestController")
@RequestMapping("/admin")
public class AdminRestController {

    private final AdminService adminService;
    private final AdminMapper adminMapper;

    /**
     * Получение всех записей об администраторах из БД.
     *
     * @return List of {@link AdminDTO} - список всех записей об администраторах в БД.
     */
    @ApiOperation(value = "Запрос для получения всех записей об администраторах", notes = "Получение всех записей об администраторах")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Записи об администраторах успешно получены"),
            @ApiResponse(code = 404, message = "Записи об администраторах не найдены")
    })
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmin() {
        List<Admin> adminList = adminService.findAll();
        if (adminList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AdminDTO> adminDTOList = adminList.stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(adminDTOList, HttpStatus.OK);
    }

    /**
     * Создание записи об администраторе в БД и получение записи в теле ответа.
     *
     * @param adminDTO - данные о новом администраторе, переданные с фронтенда.
     * @return {@link AdminDTO}
     */
    @ApiOperation(value = "Запрос для создания администратора", notes = "Создание администратора")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Администратор успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@ApiParam(value = "DTO администратора") @RequestBody @Valid AdminDTO adminDTO) {
        return new ResponseEntity<>(
                adminMapper.toDto(
                        adminService.createOrUpdateAdmin(
                                adminMapper.toEntity(adminDTO))), HttpStatus.CREATED);
    }

    /**
     * Обновление данных об администраторе в БД и получение записи об обновлённом администраторе из БД в теле ответа.
     *
     * @param adminDTO - обновлённые данные о существующем администраторе, переданные с фронтенда.
     * @return {@link AdminDTO}
     */
    @ApiOperation(value = "Запрос для обновления данных администратора", notes = "Обновление администратора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Администратор успешно обновлён"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PutMapping
    public ResponseEntity<AdminDTO> updateAdmin(@ApiParam(value = "DTO администратора") @RequestBody @Valid AdminDTO adminDTO) {
        return new ResponseEntity<>(
                adminMapper.toDto(
                        adminService.createOrUpdateAdmin(
                                adminMapper.toEntity(adminDTO))), HttpStatus.OK);
    }

    /**
     * Получение записи об администраторе из таблицы БД по переданному id.
     *
     * @param id - уникальный идентификатор администратора, по которому ищется запись из таблицы в БД.
     * @return {@link AdminDTO}
     */
    @ApiOperation(value = "Запрос для получения записи об администраторе по id", notes = "Получение администратора по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Администратор успешно получен"),
            @ApiResponse(code = 404, message = "Запись об администраторе не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@ApiParam(value = "ID администратора") @PathVariable long id) {
        Optional<Admin> admin = adminService.findById(id);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(adminMapper.toDto(admin.get()), HttpStatus.OK);
    }

    /**
     * Удаление записи об администраторе из таблицы БД по переданному id.
     *
     * @param id - уникальный идентификатор администратора, по которому ищется запись из таблицы в БД.
     * @return void
     */
    @ApiOperation(value = "Запрос для удаления записи об администраторе из таблицы в БД", notes = "Удаление записи об администраторе")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Администратор успешно удалён"),
            @ApiResponse(code = 404, message = "Запись об администраторе не найдена; возможно, запись перемещена или удалена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminById(@ApiParam(value = "ID администратора") @PathVariable long id) {
        Optional<Admin> admin = adminService.findById(id);
        if (admin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adminService.deleteAdminById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
