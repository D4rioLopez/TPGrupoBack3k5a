package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenDeCompraRequest {
    private Long usuarioId;
    private String simboloAccion;
    private Long cantidad;
    private Double precioMaximo;
    private String estado;
}
