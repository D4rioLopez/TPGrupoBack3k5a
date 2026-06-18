package ar.edu.utn.frc.back.ms_venta.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/OrdenDeCompra")
@AllArgsConstructor
public class OrdenDeCompraController {
    @PostMapping
    public ResponseEntity<?> crearOrdenDeCompra() {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarOrdenDeCompra() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrdenDeCompra() {
        return ResponseEntity.ok().build();
    }
}
