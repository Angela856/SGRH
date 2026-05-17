package cl.duocuc.dsy1103.huesped.Repository;

import cl.duocuc.dsy1103.huesped.Model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    // Buscar por documento para evitar duplicados, similar a buscar por RUT de paciente
    Optional<Huesped> findByDocumentoIdentidad(String documentoIdentidad);

}
