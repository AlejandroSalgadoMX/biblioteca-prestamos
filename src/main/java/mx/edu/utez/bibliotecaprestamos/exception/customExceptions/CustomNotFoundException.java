package mx.edu.utez.bibliotecaprestamos.exception.customExceptions;

public class CustomNotFoundException extends RuntimeException {

    public CustomNotFoundException(String message) {
        super(message);
    }
}
