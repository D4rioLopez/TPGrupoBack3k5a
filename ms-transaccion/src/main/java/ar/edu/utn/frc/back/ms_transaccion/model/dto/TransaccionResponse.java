package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TransaccionResponse {
    private Long id;
    private String compradorId;
    private String vendedorId;
    private Long cantidad;
    private Double precio;
    private LocalDateTime fecha;
}
