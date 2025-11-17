package com.example.parkingSystem.exception;

import com.example.parkingSystem.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse response = buildError(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request,
                errors
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({
            VehicleNotFoundException.class,
            ParkingLotNotFoundException.class,
            ParkingEventNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(
            RuntimeException ex,
            WebRequest request
    ) {
        ErrorResponse response = buildError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({
            NoAvailableSpotsException.class,
            VehicleAlreadyParkedException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(
            RuntimeException ex,
            WebRequest request
    ) {
        ErrorResponse response = buildError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request,
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex,
            WebRequest request
    ) {
        ErrorResponse response = buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request,
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ErrorResponse buildError(
            HttpStatus status,
            String message,
            WebRequest request,
            List<String> details
    ) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .details(details)
                .build();
    }
}
