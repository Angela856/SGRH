package cl.duocuc.dsy1103.servicioextra.service;

import cl.duocuc.dsy1103.servicioextra.model.ServicioExtra;
import cl.duocuc.dsy1103.servicioextra.repository.ServicioExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioExtraService {
    @Autowired
    private ServicioExtraRepository repository;

    public List<ServicioExtra> listarServicios() {
        return repository.findAll();
    }
    
    public ServicioExtra crear(ServicioExtra s) {
        return repository.save(s);
    }
}