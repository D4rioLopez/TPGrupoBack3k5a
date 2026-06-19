package ar.edu.utn.frc.back.ms_venta.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ordenes-de-venta")
public class OrdenDeVentaController {
    @PostMapping
    public ResponseEntity<?> crearOrdenDeVenta() {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarOrdenDeVenta() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrdenDeVenta() {
        return ResponseEntity.ok().build();
    }
}
