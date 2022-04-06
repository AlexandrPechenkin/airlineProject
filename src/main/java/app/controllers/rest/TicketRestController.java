package app.controllers.rest;

import app.entities.Ticket;
import app.entities.dtos.TicketDTO;
import app.entities.mappers.ticket.TicketMapper;
import app.exception.NoSuchObjectException;
import app.services.interfaces.TicketService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Api(tags = "TicketController")
@RequestMapping("/api/ticket")
@Validated
public class TicketRestController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;


    /**
     * метод для создания билета
     *
     * @param ticket - билет
     * @return {@link TicketDTO}
     */
    @ApiOperation(value = "Запрос для создания билета", notes = "Создание билета")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Билет успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@ApiParam(value = "DTO билета")
                                                  @Valid @RequestBody TicketDTO ticket) {
        if (Objects.isNull(ticket.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(ticketMapper.toDTO(ticketService.createOrUpdateTicket(ticketMapper.toEntity(ticket))), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }

    /**
     * метод для поиска билета по id
     *
     * @param id - идентификатор билета
     * @return {@link Ticket}
     */
    @ApiOperation(value = "Запрос для получения билета по id", notes = "Возвращение билета по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Билет не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> searchTicketById(@ApiParam(value = "Id билета", example = "1")
                                                   @PathVariable("id") Long id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }

    /**
     * обновление билета по id
     *
     * @param ticket - билет
     * @return {@link TicketDTO}
     */
    @ApiOperation(value = "Запрос для обновления билета", notes = "Обновление билета")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Билет успешно обновлен"),
            @ApiResponse(code = 400, message = "Билет неверные данные"),
    })
    @PutMapping
    public ResponseEntity<TicketDTO> updateTicket(@ApiParam(value = "DTO билета")
                                                  @Valid @RequestBody TicketDTO ticket) {
        try {
            return new ResponseEntity<>(ticketMapper.toDTO(ticketService.createOrUpdateTicket(ticketMapper.toEntity(ticket))), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }

    @GetMapping("/holdNumber/{holdNumber}")
    public ResponseEntity<Ticket> getTicketByHoldNumber(
            @ApiParam(value = "Номер бронирования билета", example = "1000")
            @PathVariable("holdNumber") Long holdNumber) {

        Ticket ticket = ticketService.findTicketByHoldNumber(holdNumber);
        if (ticket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            return ResponseEntity.ok(ticket);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }
}
