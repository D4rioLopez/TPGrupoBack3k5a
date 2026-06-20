package ar.edu.utn.frc.back.ms_venta.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransaccionRequest {
    private Long compradorId;
    private Long vendedorId;
    private Integer cantidad;
}
