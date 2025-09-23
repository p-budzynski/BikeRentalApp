package pl.kurs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kurs.dto.ExceptionResponseDto;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleDataNotFoundException(DataNotFoundException exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }
}
