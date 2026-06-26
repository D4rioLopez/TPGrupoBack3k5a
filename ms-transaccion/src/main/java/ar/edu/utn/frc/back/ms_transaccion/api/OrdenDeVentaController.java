package ar.edu.utn.frc.back.ms_transaccion.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.back.ms_transaccion.model.EstadoOrdenVenta;
import ar.edu.utn.frc.back.ms_transaccion.model.DetalleOrdenDeVenta;
import ar.edu.utn.frc.back.ms_transaccion.model.OrdenDeVenta;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.DetalleOrdenDeVentaResponse;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.OrdenDeVentaRequest;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.OrdenDeVentaResponse;
import ar.edu.utn.frc.back.ms_transaccion.service.OrdenDeVentaService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ordenes-de-venta")
public class OrdenDeVentaController {
    @Autowired
    private OrdenDeVentaService ordenDeVentaService;

    @GetMapping
    public ResponseEntity<List<OrdenDeVentaResponse>> obtenerOrdenesDeVenta(
            @RequestParam(required = false) EstadoOrdenVenta estado,
            @AuthenticationPrincipal Jwt jwt) {
        
        List<OrdenDeVenta> ordenes = ordenDeVentaService.listarOrdenesDeVenta(jwt.getSubject(), estado);

        List<OrdenDeVentaResponse> response = ordenes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<OrdenDeVentaResponse>> obtenerTodasLasOrdenesDeVenta(
            @RequestParam(required = false) EstadoOrdenVenta estado) {
        
        List<OrdenDeVenta> ordenes = ordenDeVentaService.listarOrdenesDeVenta(null, estado);

        List<OrdenDeVentaResponse> response = ordenes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    //RF4 registrar ov
    @PostMapping
    public ResponseEntity<OrdenDeVentaResponse> crearOrdenDeVenta(
            @Valid @RequestBody OrdenDeVentaRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        OrdenDeVenta ordenDeVenta = new OrdenDeVenta();
        ordenDeVenta.setKeycloakId(jwt.getSubject());
        ordenDeVenta.setDetalleOrdenDeVenta(
                request.getDetalles().stream()
                        .map(d -> {
                            DetalleOrdenDeVenta detalle = new DetalleOrdenDeVenta();
                            detalle.setSimboloAccion(d.getSimboloAccion());
                            detalle.setCantidad(d.getCantidad());
                            detalle.setPrecioUnitario(d.getPrecioUnitario());
                            return detalle;
                        })
                        .collect(Collectors.toList())
        );

        OrdenDeVenta resultado = ordenDeVentaService.crearOrdenDeVenta(ordenDeVenta);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(resultado));
    }
        // mapeo entidad a response
        private OrdenDeVentaResponse mapToResponse(OrdenDeVenta ov) {
            return OrdenDeVentaResponse.builder()
                    .id(ov.getId())
                    .keycloakId(ov.getKeycloakId())
                    .estado(ov.getEstado().name())
                    .fecha(ov.getFecha())
                    .detalles(ov.getDetalleOrdenDeVenta().stream()
                            .map(d -> DetalleOrdenDeVentaResponse.builder()
                                    .id(d.getId())
                                    .simboloAccion(d.getSimboloAccion())
                                    .cantidad(d.getCantidad())
                                    .precioUnitario(d.getPrecioUnitario())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        }
}
