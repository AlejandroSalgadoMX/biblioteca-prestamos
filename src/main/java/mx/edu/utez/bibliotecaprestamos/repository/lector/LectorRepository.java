package mx.edu.utez.bibliotecaprestamos.repository.lector;

import mx.edu.utez.bibliotecaprestamos.model.lector.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
}
