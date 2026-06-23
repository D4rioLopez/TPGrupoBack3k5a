package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrdenDeVentaRequest {
    private Long usuarioId;
    private List<DetalleOrdenDeVentaRequest> detalles;
}
