package cl.duocuc.dsy1103.reserva.service;

import cl.duocuc.dsy1103.reserva.model.Reserva;
import cl.duocuc.dsy1103.reserva.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository repository;

    public Reserva crearReserva(Reserva reserva) {
        if(reserva.getEstado() == null){ 
           reserva.setEstado("PENDIENTE");
        }
        return repository.save(reserva);
    }

    public List<Reserva> listarTodas() {
        return repository.findAll();
    }
}