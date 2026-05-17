package cl.duocuc.dsy1103.hotel.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String address;
    private String stars;
    private LocalDateTime createdAt;
}