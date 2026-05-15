package cl.duocuc.dsy1103.pago.service;

import cl.duocuc.dsy1103.pago.model.Pago;
import cl.duocuc.dsy1103.pago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PagoService {
    @Autowired
    private PagoRepository repository;

    public Pago procesarPago(Pago pago) {
        
        pago.setFechaPago(LocalDateTime.now());

        return repository.save(pago);
    }
}
