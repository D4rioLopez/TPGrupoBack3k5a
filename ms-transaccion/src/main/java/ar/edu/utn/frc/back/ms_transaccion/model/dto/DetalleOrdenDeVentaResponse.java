package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DetalleOrdenDeVentaResponse {
    private Long id;
    private String simboloAccion;
    private Long cantidad;
    private Double precioUnitario;
}
