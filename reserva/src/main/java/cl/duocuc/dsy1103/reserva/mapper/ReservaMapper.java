package cl.duocuc.dsy1103.reserva.mapper;

import cl.duocuc.dsy1103.reserva.dto.ReservaRequest;
import cl.duocuc.dsy1103.reserva.dto.ReservaResponse;
import cl.duocuc.dsy1103.reserva.model.Reserva;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ReservaMapper {

    public Reserva toEntity(ReservaRequest request) {
        if (request == null) return null;
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(request.getIdUsuario());
        reserva.setIdHabitacion(request.getIdHabitacion());
        reserva.setFechaInicio(request.getFechaInicio());
        reserva.setFechaFin(request.getFechaFin());
        reserva.setEstado("PENDIENTE");
        return reserva;
    }

    public ReservaResponse toResponse(Reserva entity) {
        if (entity == null) return null;
        return ReservaResponse.builder()
                .id(entity.getId())
                .idUsuario(entity.getIdUsuario())
                .idHabitacion(entity.getIdHabitacion())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .estado(entity.getEstado())
                .createdAt(LocalDateTime.now()) // O mapear desde tu entidad si agregas el campo
                .build();
    }
}
