package mx.edu.utez.bibliotecaprestamos.controller.prestamo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSolicitarPrestamoDto {

    @NotNull(message = "El lector es obligatorio")
    @Positive(message = "El id del lector debe ser válido")
    private Long lectorId;

    @NotNull(message = "El libro es obligatorio")
    @Positive(message = "El id del libro debe ser válido")
    private Long libroId;
}
