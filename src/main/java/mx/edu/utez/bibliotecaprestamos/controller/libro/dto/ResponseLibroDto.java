package mx.edu.utez.bibliotecaprestamos.controller.libro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLibroDto {

    private Long id;
    private String titulo;
    private int ejemplaresDisponibles;
}
