package ar.edu.utn.frc.back.ms_venta.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OrdeDeVentaResponse {
    private Long id;
    private String estado;
    private LocalDateTime fecha;
    private List<DetalleOrdenDeVentaResponse> detalles;
}
