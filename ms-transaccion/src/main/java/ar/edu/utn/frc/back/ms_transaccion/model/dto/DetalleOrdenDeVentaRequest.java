package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOrdenDeVentaRequest {
    private String simboloAccion;
    private Long cantidad;
    private Double precioUnitario;
}
