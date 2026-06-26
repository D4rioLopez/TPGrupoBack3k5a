package ar.edu.utn.frc.back.ms_transaccion.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ar.edu.utn.frc.back.ms_transaccion.model.dto.OperacionResponse;
import ar.edu.utn.frc.back.ms_transaccion.service.HistorialOperacionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/historial-operaciones")
public class HistorialOperacionController {
    @Autowired
    private HistorialOperacionService historialOperacionService;

    //RF5 historial de operaciones
    @GetMapping("/usuario/{keycloakId}")
    public ResponseEntity<List<OperacionResponse>> listarHistorialUsuario(@PathVariable String keycloakId) {
        List<OperacionResponse> historial = historialOperacionService.listarHistorialDeOperacionesDeUsuario(keycloakId);
        return ResponseEntity.ok(historial);
    }
}
