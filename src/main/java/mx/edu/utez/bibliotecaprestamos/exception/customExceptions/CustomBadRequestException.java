package mx.edu.utez.bibliotecaprestamos.exception.customExceptions;

public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(String message) {
        super(message);
    }
}
