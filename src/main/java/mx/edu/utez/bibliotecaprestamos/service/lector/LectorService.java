package mx.edu.utez.bibliotecaprestamos.service.lector;

import mx.edu.utez.bibliotecaprestamos.controller.lector.dto.RequestCreateLectorDto;
import mx.edu.utez.bibliotecaprestamos.controller.lector.dto.ResponseLectorDto;
import mx.edu.utez.bibliotecaprestamos.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.bibliotecaprestamos.model.lector.Lector;
import mx.edu.utez.bibliotecaprestamos.repository.lector.LectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LectorService {

    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    @Transactional
    public ResponseLectorDto crearLector(RequestCreateLectorDto payload) {
        Lector lector = new Lector();
        lector.setNombre(payload.getNombre().trim());
        lector.setActivo(payload.getActivo());
        lector.setMembresiaPremium(payload.getMembresiaPremium());
        return convertirLector(lectorRepository.save(lector));
    }

    @Transactional
    public ResponseLectorDto rehabilitarLector(Long id) {
        Lector lector = obtenerLector(id);
        lector.setActivo(true);
        return convertirLector(lectorRepository.save(lector));
    }

    private Lector obtenerLector(Long id) {
        return lectorRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El lector no existe."));
    }

    private ResponseLectorDto convertirLector(Lector lector) {
        return new ResponseLectorDto(
                lector.getId(), lector.getNombre(), lector.isActivo(), lector.isMembresiaPremium()
        );
    }
}
