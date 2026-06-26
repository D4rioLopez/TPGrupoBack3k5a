package ar.edu.utn.frc.back.ms_transaccion.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.back.ms_transaccion.model.OrdenDeCompra;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.OrdenDeCompraRequest;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.OrdenDeCompraResponse;
import ar.edu.utn.frc.back.ms_transaccion.service.OrdenDeCompraService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ordenes-de-compra")
public class OrdenDeCompraController {
    @Autowired
    private OrdenDeCompraService ordenDeCompraService;

    //RF3 registrar oc
    @PostMapping
    public ResponseEntity<OrdenDeCompraResponse> crearOrdenDeCompra(
            @RequestBody OrdenDeCompraRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        OrdenDeCompra ordenDeCompra = new OrdenDeCompra();
        ordenDeCompra.setKeycloakId(jwt.getSubject());
        ordenDeCompra.setSimboloAccion(request.getSimboloAccion());
        ordenDeCompra.setCantidad(request.getCantidad());
        ordenDeCompra.setPrecioMaximo(request.getPrecioMaximo());
        ordenDeCompra.setFecha(LocalDateTime.now());

        OrdenDeCompra resultado = ordenDeCompraService.procesarOrdenDeCompra(ordenDeCompra);

        return ResponseEntity.ok(mapToResponse(resultado));
    }

    private OrdenDeCompraResponse mapToResponse(OrdenDeCompra oc) {
        return OrdenDeCompraResponse.builder()
                .id(oc.getId())
                .simboloAccion(oc.getSimboloAccion())
                .cantidad(oc.getCantidad())
                .precioMaximo(oc.getPrecioMaximo())
                .estado(oc.getEstado().name())
                .build();
    }
}
