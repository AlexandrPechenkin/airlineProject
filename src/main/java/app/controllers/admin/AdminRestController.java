package app.controllers.admin;

import app.entities.users.admin.dto.AdminDTO;
import app.mappers.users.admin.AdminMapper;
import app.services.interfaces.AdminService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Api(tags = "AdminRestController")
@RequestMapping("/admin")
public class AdminRestController {

    private final AdminService adminService;
    private final AdminMapper adminMapper;

    @ApiOperation(value = "Запрос для создания администратора", notes = "Создание администратора")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Администратор успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@ApiParam(value = "DTO администратора") @RequestBody @Valid AdminDTO adminDTO) {
        if (Objects.nonNull(adminDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                adminMapper.adminEntityToAdminDto(
                        adminService.createOrUpdateAdmin(
                                adminMapper.adminDtoToAdminEntity(adminDTO))), HttpStatus.CREATED);
    }

}
