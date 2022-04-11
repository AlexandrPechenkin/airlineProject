package app.controllers.rest;

import app.entities.Role;
import app.services.interfaces.RoleService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
     * @return List of {@link Role}
     */
    @ApiOperation(value = "Запрос для получения всех записей о ролях",
            notes = "Получение всех записей о ролях")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    /**
     *
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
}