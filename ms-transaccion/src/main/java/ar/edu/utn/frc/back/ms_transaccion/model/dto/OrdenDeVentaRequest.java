package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrdenDeVentaRequest {
    private String keycloakId;

    @NotEmpty(message = "La orden de venta debe contener al menos un detalle")
    @Valid
    private List<DetalleOrdenDeVentaRequest> detalles;
}
