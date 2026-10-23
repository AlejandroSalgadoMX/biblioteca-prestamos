package mx.edu.utez.bibliotecaprestamos.service.libro;

import mx.edu.utez.bibliotecaprestamos.controller.libro.dto.RequestCreateLibroDto;
import mx.edu.utez.bibliotecaprestamos.controller.libro.dto.ResponseLibroDto;
import mx.edu.utez.bibliotecaprestamos.model.libro.Libro;
import mx.edu.utez.bibliotecaprestamos.repository.libro.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Transactional
    public ResponseLibroDto crearLibro(RequestCreateLibroDto payload) {
        Libro libro = new Libro();
        libro.setTitulo(payload.getTitulo().trim());
        libro.setEjemplaresDisponibles(payload.getEjemplaresDisponibles());
        return convertirLibro(libroRepository.save(libro));
    }

    private ResponseLibroDto convertirLibro(Libro libro) {
        return new ResponseLibroDto(libro.getId(), libro.getTitulo(), libro.getEjemplaresDisponibles());
    }
}
