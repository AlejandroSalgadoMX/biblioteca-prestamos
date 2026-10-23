package mx.edu.utez.bibliotecaprestamos.controller.prestamo.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.bibliotecaprestamos.model.prestamo.EstadoPrestamo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePrestamoDto {

    private Long id;
    private Long lectorId;
    private Long libroId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionMaxima;
    private EstadoPrestamo estado;
}
