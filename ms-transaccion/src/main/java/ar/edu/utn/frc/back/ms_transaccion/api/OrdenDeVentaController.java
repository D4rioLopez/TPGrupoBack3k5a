package ar.edu.utn.frc.back.ms_transaccion.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.back.ms_transaccion.model.DetalleOrdenDeVenta;
import ar.edu.utn.frc.back.ms_transaccion.model.OrdenDeVenta;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.DetalleOrdenDeVentaResponse;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.OrdenDeVentaRequest;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.OrdenDeVentaResponse;
import ar.edu.utn.frc.back.ms_transaccion.service.OrdenDeVentaService;

import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ordenes-de-venta")
public class OrdenDeVentaController {
    private OrdenDeVentaService ordenDeVentaService;

    //RF4 registrar ov
    @PostMapping
    public ResponseEntity<OrdenDeVentaResponse> crearOrdenDeVenta(@RequestBody OrdenDeVentaRequest request) {
        OrdenDeVenta ordenDeVenta = new OrdenDeVenta();
        ordenDeVenta.setUsuarioId(request.getUsuarioId());
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
                    .usuarioId(ov.getUsuarioId())
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
