package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class OperacionResponse {
    private Long id;
    private String tipo;        // "COMPRA", "VENTA", "TRANSACCION"
    private String simboloAccion;
    private Long cantidad;
    private Double precio;
    private LocalDateTime fecha;
    private String estado;      // solo aplica a OC y OV
}
