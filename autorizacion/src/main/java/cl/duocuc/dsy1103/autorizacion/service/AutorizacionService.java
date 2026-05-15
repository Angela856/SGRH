package cl.duocuc.dsy1103.autorizacion.service;

import cl.duocuc.dsy1103.autorizacion.model.Usuario;
import cl.duocuc.dsy1103.autorizacion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorizacionService {
    @Autowired private UsuarioRepository repository;
    public Usuario registrar(Usuario u) { return repository.save(u); }
    public boolean login(String correo, String pass) {
        return repository.findByCorreo(correo).map(u -> u.getContrasena().equals(pass)).orElse(false);
    }
}