package cl.duocuc.dsy1103.pago.repository;

import cl.duocuc.dsy1103.pago.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}