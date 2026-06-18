package ar.edu.utn.frc.back.ms_venta.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrdenDeVentaRequest {
    private List<DetalleOrdenDeVentaRequest> detalles;
}
