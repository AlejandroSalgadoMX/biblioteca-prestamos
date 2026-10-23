package mx.edu.utez.bibliotecaprestamos.controller.prestamo;

import java.util.List;
import jakarta.validation.Valid;
import mx.edu.utez.bibliotecaprestamos.controller.prestamo.dto.RequestSolicitarPrestamoDto;
import mx.edu.utez.bibliotecaprestamos.controller.prestamo.dto.ResponsePrestamoDto;
import mx.edu.utez.bibliotecaprestamos.service.prestamo.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/prestamo")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<ResponsePrestamoDto> solicitarPrestamo(
            @RequestBody @Valid RequestSolicitarPrestamoDto payload
    ) {
        return ResponseEntity.status(201).body(prestamoService.solicitarPrestamo(payload));
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<ResponsePrestamoDto> devolverLibro(@PathVariable Long id) {
        return ResponseEntity.status(200).body(prestamoService.devolverLibro(id));
    }

    @PatchMapping("/{id}/renovar")
    public ResponseEntity<ResponsePrestamoDto> renovarPrestamo(@PathVariable Long id) {
        return ResponseEntity.status(200).body(prestamoService.renovarPrestamo(id));
    }

    @GetMapping("/lector/{lectorId}/activos")
    public ResponseEntity<List<ResponsePrestamoDto>> listarPrestamosActivosLector(@PathVariable Long lectorId) {
        return ResponseEntity.status(200).body(prestamoService.listarPrestamosActivosLector(lectorId));
    }
}
