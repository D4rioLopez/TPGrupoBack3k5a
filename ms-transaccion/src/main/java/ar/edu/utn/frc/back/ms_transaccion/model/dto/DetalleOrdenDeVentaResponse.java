package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DetalleOrdenDeVentaResponse {
    private Long id;
    private String simboloAccion;
    private Long cantidad;
    private Double precioUnitario;
}
