package ar.edu.utn.frc.back.ms_venta.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
