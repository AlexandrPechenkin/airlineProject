package app.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик ошибок
 * Как с ним работать.
 * https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html
 * <p>
 * Создаем метод, а над ним ставим аннотацию @ExceptionHandler в ней указываем класс обрабатываемых исключений.
 * В методе компонуем в {@link ApiError} и возвращаем HTTP ответ.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает ошибки валидации аргументов метода {@link MethodArgumentNotValidException}
     *
     * @return - 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        ApiError err = new ApiError(LocalDateTime.now(),
                "Ошибка валидации",
                ex.getFieldErrors()
                        .stream()
                        .map(it -> it.getField() + ": " + it.getDefaultMessage())
                        .collect(Collectors.toList()));
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
