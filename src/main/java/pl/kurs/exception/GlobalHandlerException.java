package pl.kurs.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kurs.dto.ExceptionResponseDto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleDataNotFoundException(DataNotFoundException exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(OverlappingReservationException.class)
    public ResponseEntity<ExceptionResponseDto> handleOverlappingReservationException(OverlappingReservationException exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(EntityCannotBeDeleteException.class)
    public ResponseEntity<ExceptionResponseDto> handleEntityCannotBeDeleteException(EntityCannotBeDeleteException exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(exception.getMessage(), HttpStatus.CONFLICT.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMessages = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> {
                    if (err instanceof FieldError fieldError) {
                        return fieldError.getDefaultMessage();
                    }
                    return err.getDefaultMessage();
                })
                .collect(Collectors.joining("; "));

        ExceptionResponseDto response = new ExceptionResponseDto(errorMessages, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleConstraintViolationException(ConstraintViolationException exception) {
        String errorMessages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        ExceptionResponseDto response = new ExceptionResponseDto(errorMessages, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        String message = exception.getMessage();

        if (message != null && message.contains("bike_details_vin_number_key")) {
            ExceptionResponseDto response = new ExceptionResponseDto(
                    "Vin number already exists", HttpStatus.CONFLICT.toString(), LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
        }

        if (message != null && message.contains("client_data_phone_number_key")) {
            ExceptionResponseDto response = new ExceptionResponseDto(
                    "Phone number already exists",
                    HttpStatus.CONFLICT.toString(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
        }

        if (message != null && message.contains("client_data_pesel_key")) {
            ExceptionResponseDto response = new ExceptionResponseDto(
                    "PESEl number already exists",
                    HttpStatus.CONFLICT.toString(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
        }

        ExceptionResponseDto response = new ExceptionResponseDto(
                "Data integrity violation", HttpStatus.CONFLICT.toString(), LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
    }

}
