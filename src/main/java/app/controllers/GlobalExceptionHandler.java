package app.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик ошибок
 */

/*
 TODO: Нужно в Readme расписать подробно для всех остальных участников команды, как с этим работать.
 TODO: перенести в пакет util - он как раз для таких вещей.
 TODO: подумать над созданием кастомных исключений для наших целей
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Обрабатывает ошибки валидации {@link ConstraintViolationException}
     * @return - 400
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiError err = new ApiError(LocalDateTime.now(),
                "Ошибка валидации",
                List.of(ex.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessageTemplate)
                        .collect(Collectors.joining(", "))));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    /**
     * Внутренний класс отображения ошибки
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ApiError {
        /**
         * Время
         */
        @DateTimeFormat(pattern = "dd:MM:yyyy hh:mm:ss")
        private LocalDateTime timestamp;
        /**
         * Сообщение
         */
        private String message;
        /**
         * Список ошибок
         */
        private List<String> errors;
    }
}
