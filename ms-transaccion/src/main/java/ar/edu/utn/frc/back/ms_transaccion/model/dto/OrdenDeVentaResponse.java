package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrdenDeVentaResponse {
    private Long id;
    private String keycloakId;
    private String estado;
    private LocalDateTime fecha;
    private List<DetalleOrdenDeVentaResponse> detalles;
}
