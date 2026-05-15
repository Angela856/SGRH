package cl.duocuc.dsy1103.hotel.repository;

import cl.duocuc.dsy1103.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}