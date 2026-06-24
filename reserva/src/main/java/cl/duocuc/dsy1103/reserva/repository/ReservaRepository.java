package cl.duocuc.dsy1103.reserva.repository;

import cl.duocuc.dsy1103.reserva.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}