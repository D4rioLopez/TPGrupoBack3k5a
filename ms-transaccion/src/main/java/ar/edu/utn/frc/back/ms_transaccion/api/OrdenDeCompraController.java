package ar.edu.utn.frc.back.ms_transaccion.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.back.ms_transaccion.model.EstadoOrdenCompra;
import ar.edu.utn.frc.back.ms_transaccion.model.OrdenDeCompra;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.OrdenDeCompraRequest;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.OrdenDeCompraResponse;
import ar.edu.utn.frc.back.ms_transaccion.service.OrdenDeCompraService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ordenes-de-compra")
public class OrdenDeCompraController {
    @Autowired
    private OrdenDeCompraService ordenDeCompraService;

    @GetMapping
    public ResponseEntity<List<OrdenDeCompraResponse>> obtenerOrdenesDeCompra(
            @RequestParam(required = false) EstadoOrdenCompra estado,
            @AuthenticationPrincipal Jwt jwt) {
        
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        boolean isAdmin = false;
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            isAdmin = roles.contains("admin");
        }

        List<OrdenDeCompra> ordenes;
        if (isAdmin) {
            ordenes = ordenDeCompraService.listarOrdenesDeCompra(null, estado);
        } else {
            ordenes = ordenDeCompraService.listarOrdenesDeCompra(jwt.getSubject(), estado);
        }

        List<OrdenDeCompraResponse> response = ordenes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

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
                .keycloakId(oc.getKeycloakId())
                .simboloAccion(oc.getSimboloAccion())
                .cantidad(oc.getCantidad())
                .precioMaximo(oc.getPrecioMaximo())
                .estado(oc.getEstado().name())
                .build();
    }
}
