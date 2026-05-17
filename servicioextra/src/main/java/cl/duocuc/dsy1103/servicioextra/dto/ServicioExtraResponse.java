package cl.duocuc.dsy1103.servicioextra.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioExtraResponse {
    private Long id;
    private String descripcion;
    private Double precio;
    private LocalDateTime createdAt;
}