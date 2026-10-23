package mx.edu.utez.bibliotecaprestamos.controller.libro;

import jakarta.validation.Valid;
import mx.edu.utez.bibliotecaprestamos.controller.libro.dto.RequestCreateLibroDto;
import mx.edu.utez.bibliotecaprestamos.controller.libro.dto.ResponseLibroDto;
import mx.edu.utez.bibliotecaprestamos.service.libro.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/libro")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<ResponseLibroDto> crearLibro(
            @RequestBody @Valid RequestCreateLibroDto payload
    ) {
        return ResponseEntity.status(201).body(libroService.crearLibro(payload));
    }
}
