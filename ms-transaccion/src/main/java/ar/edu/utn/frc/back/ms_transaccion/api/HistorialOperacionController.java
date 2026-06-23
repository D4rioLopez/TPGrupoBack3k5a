package ar.edu.utn.frc.back.ms_transaccion.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ar.edu.utn.frc.back.ms_transaccion.model.dto.OperacionResponse;
import ar.edu.utn.frc.back.ms_transaccion.service.HistorialOperacionService;

import java.util.List;

@Service
public class HistorialOperacionController {
    @Autowired
    private HistorialOperacionService historialOperacionService;

    //RF5 historial de operaciones
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<OperacionResponse>> listarHistorialUsuario(@PathVariable Long usuarioId) {
        List<OperacionResponse> historial = historialOperacionService.listarHistorialDeOperacionesDeUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }
}
