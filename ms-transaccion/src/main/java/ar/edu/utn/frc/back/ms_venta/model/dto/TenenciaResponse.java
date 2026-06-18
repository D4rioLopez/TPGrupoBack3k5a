package ar.edu.utn.frc.back.ms_venta.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TenenciaResponse {
    private String simboloAccion;
    private Integer cantidad;
}
