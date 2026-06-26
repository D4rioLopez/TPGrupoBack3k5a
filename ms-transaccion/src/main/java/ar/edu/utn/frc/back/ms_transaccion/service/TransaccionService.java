package ar.edu.utn.frc.back.ms_transaccion.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.back.ms_transaccion.model.Transaccion;
import ar.edu.utn.frc.back.ms_transaccion.repository.TransaccionRepository;

import java.util.List;

@Service
public class TransaccionService {
    @Autowired
    private TransaccionRepository transaccionRepository;

    public List<Transaccion> listarTodasTransacciones() {
        return transaccionRepository.findByOrderByFechaDesc();
    }

    public List<Transaccion> listarPorkeycloakId(String keycloakId) {
        return transaccionRepository.findHistorialBykeycloakId(keycloakId);
    }
}
