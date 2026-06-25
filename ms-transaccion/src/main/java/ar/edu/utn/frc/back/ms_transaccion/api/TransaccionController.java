package ar.edu.utn.frc.back.ms_transaccion.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.back.ms_transaccion.model.Transaccion;
import ar.edu.utn.frc.back.ms_transaccion.model.dto.TransaccionResponse;
import ar.edu.utn.frc.back.ms_transaccion.service.TransaccionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TransaccionResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<TransaccionResponse> historial = transaccionService.listarPorUsuarioId(usuarioId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historial);
    }

    @GetMapping
    public ResponseEntity<List<TransaccionResponse>> listarTodas() {
        List<TransaccionResponse> transacciones = transaccionService.listarTodasTransacciones()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transacciones);
    }

    private TransaccionResponse mapToResponse(Transaccion t) {
        return TransaccionResponse.builder()
                .id(t.getId())
                .compradorId(t.getOrdenDeCompra().getUsuarioId())
                .vendedorId(t.getDetalleOrdenDeVenta().getOrdenDeVenta().getUsuarioId())
                .cantidad(t.getCantidad())
                .precio(t.getPrecio())
                .fecha(t.getFecha())
                .build();
    }
}
