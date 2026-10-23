package mx.edu.utez.bibliotecaprestamos.repository.prestamo;

import java.util.Collection;
import java.util.List;
import mx.edu.utez.bibliotecaprestamos.model.prestamo.EstadoPrestamo;
import mx.edu.utez.bibliotecaprestamos.model.prestamo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    long countByLectorIdAndEstadoIn(Long lectorId, Collection<EstadoPrestamo> estados);

    List<Prestamo> findByLectorIdAndEstadoInOrderByFechaPrestamoDesc(
            Long lectorId, Collection<EstadoPrestamo> estados
    );
}
