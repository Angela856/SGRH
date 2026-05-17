package cl.duocuc.dsy1103.comentario.Repository;
import cl.duocuc.dsy1103.comentario.Model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByHabitacionId(Long habitacionId);
}


