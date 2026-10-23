package mx.edu.utez.bibliotecaprestamos.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import mx.edu.utez.bibliotecaprestamos.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.bibliotecaprestamos.exception.customExceptions.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonInvalido(HttpMessageNotReadableException exception) {
        return error(HttpStatus.BAD_REQUEST, "El cuerpo JSON es inválido.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(MethodArgumentNotValidException exception) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<Map<String, String>> manejarCustomBadRequest(CustomBadRequestException exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarCustomNotFound(CustomNotFoundException exception) {
        return error(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    private ResponseEntity<Map<String, String>> error(HttpStatus status, String mensaje) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", mensaje);
        return ResponseEntity.status(status).body(errores);
    }
}
