package mx.edu.utez.bibliotecaprestamos.service.prestamo;

import java.time.LocalDate;
import java.util.List;
import mx.edu.utez.bibliotecaprestamos.controller.prestamo.dto.RequestSolicitarPrestamoDto;
import mx.edu.utez.bibliotecaprestamos.controller.prestamo.dto.ResponsePrestamoDto;
import mx.edu.utez.bibliotecaprestamos.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.bibliotecaprestamos.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.bibliotecaprestamos.model.lector.Lector;
import mx.edu.utez.bibliotecaprestamos.model.libro.Libro;
import mx.edu.utez.bibliotecaprestamos.model.prestamo.EstadoPrestamo;
import mx.edu.utez.bibliotecaprestamos.model.prestamo.Prestamo;
import mx.edu.utez.bibliotecaprestamos.repository.lector.LectorRepository;
import mx.edu.utez.bibliotecaprestamos.repository.libro.LibroRepository;
import mx.edu.utez.bibliotecaprestamos.repository.prestamo.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    private static final List<EstadoPrestamo> ESTADOS_ACTIVOS = List.of(
            EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO
    );

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(
            PrestamoRepository prestamoRepository,
            LectorRepository lectorRepository,
            LibroRepository libroRepository
    ) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public ResponsePrestamoDto solicitarPrestamo(RequestSolicitarPrestamoDto payload) {
        Lector lector = obtenerLector(payload.getLectorId());
        Libro libro = obtenerLibro(payload.getLibroId());

        if (!lector.isActivo()) {
            throw new CustomBadRequestException("El lector no está activo.");
        }
        if (libro.getEjemplaresDisponibles() < 1) {
            throw new CustomBadRequestException("El libro no tiene ejemplares disponibles.");
        }

        long prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(lector.getId(), ESTADOS_ACTIVOS);
        int limite = lector.isMembresiaPremium() ? 5 : 3;
        if (prestamosActivos >= limite) {
            throw new CustomBadRequestException("El lector ya alcanzó el límite de " + limite + " préstamos activos.");
        }

        LocalDate fechaActual = LocalDate.now();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaActual);
        prestamo.setFechaDevolucionMaxima(fechaActual.plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return convertirPrestamo(prestamoRepository.save(prestamo));
    }

    @Transactional
    public ResponsePrestamoDto devolverLibro(Long id) {
        Prestamo prestamo = obtenerPrestamo(id);
        if (prestamo.getEstado() != EstadoPrestamo.PRESTADO && prestamo.getEstado() != EstadoPrestamo.VENCIDO) {
            throw new CustomBadRequestException("El préstamo ya fue devuelto.");
        }

        boolean entregaTardia = LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima());
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        if (entregaTardia) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return convertirPrestamo(prestamoRepository.save(prestamo));
    }

    @Transactional(noRollbackFor = CustomBadRequestException.class)
    public ResponsePrestamoDto renovarPrestamo(Long id) {
        Prestamo prestamo = obtenerPrestamo(id);
        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new CustomBadRequestException("No se puede renovar un préstamo vencido o devuelto.");
        }
        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            prestamo.setEstado(EstadoPrestamo.VENCIDO);
            prestamoRepository.save(prestamo);
            throw new CustomBadRequestException("No se puede renovar un préstamo vencido.");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        return convertirPrestamo(prestamoRepository.save(prestamo));
    }

    @Transactional
    public List<ResponsePrestamoDto> listarPrestamosActivosLector(Long lectorId) {
        obtenerLector(lectorId);
        List<Prestamo> prestamos = prestamoRepository
                .findByLectorIdAndEstadoInOrderByFechaPrestamoDesc(lectorId, ESTADOS_ACTIVOS);

        boolean hayPrestamosVencidos = false;
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getEstado() == EstadoPrestamo.PRESTADO
                    && LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
                prestamo.setEstado(EstadoPrestamo.VENCIDO);
                hayPrestamosVencidos = true;
            }
        }
        if (hayPrestamosVencidos) {
            prestamoRepository.saveAll(prestamos);
        }

        return prestamos
                .stream()
                .map(this::convertirPrestamo)
                .toList();
    }

    private Lector obtenerLector(Long id) {
        return lectorRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El lector no existe."));
    }

    private Libro obtenerLibro(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El libro no existe."));
    }

    private Prestamo obtenerPrestamo(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El préstamo no existe."));
    }

    private ResponsePrestamoDto convertirPrestamo(Prestamo prestamo) {
        return new ResponsePrestamoDto(
                prestamo.getId(),
                prestamo.getLector().getId(),
                prestamo.getLibro().getId(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucionMaxima(),
                prestamo.getEstado()
        );
    }
}
