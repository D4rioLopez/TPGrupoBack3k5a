package ar.edu.utn.frc.back.ms_venta.api;

import ar.edu.utn.frc.back.ms_venta.model.Transaccion;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transacciones")
public class TransaccionController {
    @PostMapping
    public ResponseEntity<?> crearTransaccion(@RequestBody Transaccion transaccion) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarTransaccion(@RequestBody Transaccion transaccion) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTransaccion() {
        return ResponseEntity.ok().build();
    }
}
