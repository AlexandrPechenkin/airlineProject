package app.controllers.rest;

import app.entities.Admin;
import app.entities.Role;
import app.entities.dtos.AdminDTO;
import app.entities.dtos.PassengerDTO;
import app.services.interfaces.RoleService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RestController для класса {@link Role}
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "RoleRestController")
@RequestMapping("/api/role")
public class RoleRestController {

    private final RoleService roleService;

    /**
     * Получить все роли
     *
     * @return List of {@link Role}
     */
    @ApiOperation(value = "Запрос для получения всех записей о ролях",
            notes = "Получение всех записей о ролях")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    /**
     * @param id идентификатор роли
     * @return {@link Role}
     */
    @ApiOperation(value = "Запрос на получение роли по id",
            notes = "Получение роли по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Роль успешно получена"),
            @ApiResponse(code = 400, message = "Роль не найдена"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@ApiParam(value = "id роли", example = "1") @PathVariable long id) {
        Optional<Role> role = roleService.getRoleById(id);
        if (role.isEmpty()) {
            log.error("Роль с запрошенным id в БД отсутствует");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(role.get());
    }


    /**
     * Метод создает роль
     *
     * @param role - Роль
     * @return {@link Role}
     */
    @ApiOperation(value = "Запрос для создания роли", notes = "Создание роли")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Роль успешно создана"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<Role> createRole(@ApiParam(value = "Role") @RequestBody @Valid Role role) {
        if (Objects.nonNull(role.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(roleService.createOrUpdateRole(role), HttpStatus.CREATED);
    }


    /**
     * Обновление данных о ролях
     *
     * @param role - обновлённые данные о роли
     * @return {@link Role}
     */
    @ApiOperation(value = "Запрос для обновления данных роли", notes = "Обновление роли")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Роль успешно обновлёна"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PutMapping
    public ResponseEntity<Role> updateRole(@ApiParam(value = "DTO администратора")
                                           @RequestBody @Valid Role role) {
        return new ResponseEntity<>(roleService.createOrUpdateRole(role), HttpStatus.OK);
    }

    /**
     * Удаление записи об роли из таблицы БД по переданному id.
     *
     * @param id - уникальный идентификатор роли, по которому ищется запись из таблицы в БД.
     * @return void
     */
    @ApiOperation(value = "Запрос для удаления записи об роли из таблицы в БД",
            notes = "Удаление записи о роли")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Роль успешно удалёна"),
            @ApiResponse(code = 404, message = "Запись о роли не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@ApiParam(example = "1", value = "ID роли")
                                               @PathVariable long id) {
        Optional<Role> role = roleService.getRoleById(id);
        if (role.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roleService.deleteRoleById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Добавление ролей в селектор.
     *
     * @return void
     */
    @PostMapping(value = "/all")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

}