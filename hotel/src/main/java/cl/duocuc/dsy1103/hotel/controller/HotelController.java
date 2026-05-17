package cl.duocuc.dsy1103.hotel.controller;

import cl.duocuc.dsy1103.hotel.dto.HotelRequest;
import cl.duocuc.dsy1103.hotel.dto.HotelResponse;
import cl.duocuc.dsy1103.hotel.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hoteles")
public class HotelController {

    @Autowired
    private HotelService service;

    @GetMapping
    public List<HotelResponse> obtenerTodos() {
        return service.obtenerTodosLosHoteles();
    }

    @GetMapping("/{id}")
    public HotelResponse obtenerPorId(@PathVariable Long id) {
        return service.obtenerHotelPorId(id);
    }

    @PostMapping
    public HotelResponse crear(@Valid @RequestBody HotelRequest request) {
        return service.crearHotel(request);
    }

    @PutMapping("/{id}")
    public HotelResponse actualizar(@PathVariable Long id, @Valid @RequestBody HotelRequest request) {
        return service.actualizarHotel(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarHotel(id);
    }
}