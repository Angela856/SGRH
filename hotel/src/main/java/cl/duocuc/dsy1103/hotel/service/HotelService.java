package cl.duocuc.dsy1103.hotel.service;

import cl.duocuc.dsy1103.hotel.model.Hotel;
import cl.duocuc.dsy1103.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelService {
    @Autowired
    private HotelRepository repository;

    public List<Hotel> obtenerTodos() {
        return repository.findAll();
    }

    public Hotel guardarHotel(Hotel hotel) {
        return repository.save(hotel);
    }
}