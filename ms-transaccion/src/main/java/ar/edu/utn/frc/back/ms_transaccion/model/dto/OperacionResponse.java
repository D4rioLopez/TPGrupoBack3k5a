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
public class OperacionResponse {
    private Long id;
    private String tipo;        // "COMPRA", "VENTA", "TRANSACCION"
    private String simboloAccion;
    private Long cantidad;
    private Double precio;
    private LocalDateTime fecha;
    private String estado;      // solo aplica a OC y OV
}
