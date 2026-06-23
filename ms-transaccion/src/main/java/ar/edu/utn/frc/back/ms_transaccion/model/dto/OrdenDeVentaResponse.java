package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OrdenDeVentaResponse {
    private Long id;
    private Long usuarioId;
    private String estado;
    private LocalDateTime fecha;
    private List<DetalleOrdenDeVentaResponse> detalles;
}
