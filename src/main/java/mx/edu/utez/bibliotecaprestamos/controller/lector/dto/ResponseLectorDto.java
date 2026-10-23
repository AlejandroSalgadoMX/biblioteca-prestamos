package mx.edu.utez.bibliotecaprestamos.controller.lector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLectorDto {

    private Long id;
    private String nombre;
    private boolean activo;
    private boolean membresiaPremium;
}
