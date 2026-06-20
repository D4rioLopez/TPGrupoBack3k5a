package ar.edu.utn.frc.back.ms_venta.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TransaccionResponse {
    private Long id;
    private Long compradorId;
    private Long vendedorId;
    private Integer cantidad;
    private Double precio;
    private LocalDateTime fecha;
}
