package cl.duocuc.dsy1103.hotel.controller;

import cl.duocuc.dsy1103.hotel.model.Hotel;
import cl.duocuc.dsy1103.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
    @Autowired
    private HotelService service;

    @GetMapping
    public List<Hotel> obtenerTodos() {
        return service.obtenerTodos();
    }
}