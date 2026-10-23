package mx.edu.utez.bibliotecaprestamos.repository.libro;

import mx.edu.utez.bibliotecaprestamos.model.libro.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
