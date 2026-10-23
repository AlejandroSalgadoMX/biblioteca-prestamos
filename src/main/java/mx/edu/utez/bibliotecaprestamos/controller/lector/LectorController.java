package mx.edu.utez.bibliotecaprestamos.controller.lector;

import jakarta.validation.Valid;
import mx.edu.utez.bibliotecaprestamos.controller.lector.dto.RequestCreateLectorDto;
import mx.edu.utez.bibliotecaprestamos.controller.lector.dto.ResponseLectorDto;
import mx.edu.utez.bibliotecaprestamos.service.lector.LectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/lector")
public class LectorController {

    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @PostMapping
    public ResponseEntity<ResponseLectorDto> crearLector(
            @RequestBody @Valid RequestCreateLectorDto payload
    ) {
        return ResponseEntity.status(201).body(lectorService.crearLector(payload));
    }

    @PatchMapping("/{id}/rehabilitar")
    public ResponseEntity<ResponseLectorDto> rehabilitarLector(@PathVariable Long id) {
        return ResponseEntity.status(200).body(lectorService.rehabilitarLector(id));
    }
}
