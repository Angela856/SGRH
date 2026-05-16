package cl.duocuc.dsy1103.pago.mapper;

import cl.duocuc.dsy1103.pago.dto.PagoRequest;
import cl.duocuc.dsy1103.pago.dto.PagoResponse;
import cl.duocuc.dsy1103.pago.model.Pago;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class PagoMapper {

    public Pago toEntity(PagoRequest request) {
        if (request == null) {
            return null;
        }
        return Pago.builder()
                .idReserva(request.getIdReserva())
                .monto(request.getMonto())
                .metodo(request.getMetodo())
                .fechaPago(LocalDateTime.now())
                .build();
    }

    public PagoResponse toResponse(Pago entity) {
        if (entity == null) {
            return null;
        }
        return PagoResponse.builder()
                .id(entity.getId())
                .idReserva(entity.getIdReserva())
                .monto(entity.getMonto())
                .metodo(entity.getMetodo())
                .estado("APROBADO")
                .fechaPago(entity.getFechaPago())
                .build();
    }
}