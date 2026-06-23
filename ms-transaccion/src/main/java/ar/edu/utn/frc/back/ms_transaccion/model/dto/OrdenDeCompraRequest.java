package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrdenDeCompraRequest {
    private Long usuarioId;
    private String simboloAccion;
    private Long cantidad;
    private Double precioMaximo;
    private String estado;
}
