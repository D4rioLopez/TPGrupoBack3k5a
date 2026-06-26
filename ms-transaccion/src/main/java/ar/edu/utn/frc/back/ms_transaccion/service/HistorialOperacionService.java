package ar.edu.utn.frc.back.ms_transaccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frc.back.ms_transaccion.model.dto.OperacionResponse;
import ar.edu.utn.frc.back.ms_transaccion.repository.OrdenDeCompraRepository;
import ar.edu.utn.frc.back.ms_transaccion.repository.OrdenDeVentaRepository;
import ar.edu.utn.frc.back.ms_transaccion.repository.TransaccionRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class HistorialOperacionService {
    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;
    @Autowired
    private OrdenDeVentaRepository ordenDeVentaRepository;
    @Autowired
    private TransaccionRepository transaccionRepository;

    //RF5 historial de operaciones
    @Transactional
    public List<OperacionResponse> listarHistorialDeOperacionesDeUsuario(String keycloakId) {
        List<OperacionResponse> historial = new ArrayList<>();

        //oc
        ordenDeCompraRepository.findByKeycloakIdOrderByFechaDesc(keycloakId)
                .forEach(oc -> historial.add(OperacionResponse.builder()
                        .id(oc.getId())
                        .tipo("COMPRA")
                        .simboloAccion(oc.getSimboloAccion())
                        .cantidad(oc.getCantidad())
                        .precio(oc.getPrecioMaximo())
                        .fecha(oc.getFecha())
                        .estado(oc.getEstado().name())
                        .build()));

        //OVs
        ordenDeVentaRepository.findByKeycloakIdOrderByFechaDesc(keycloakId)
                .forEach(ov -> ov.getDetalleOrdenDeVenta()
                        .forEach(detalle -> historial.add(OperacionResponse.builder()
                                .id(ov.getId())
                                .tipo("VENTA")
                                .simboloAccion(detalle.getSimboloAccion())
                                .cantidad(detalle.getCantidad())
                                .precio(detalle.getPrecioUnitario())
                                .fecha(ov.getFecha())
                                .estado(ov.getEstado().name())
                                .build())));

        // transacciones
        transaccionRepository.findHistorialBykeycloakId(keycloakId)
                .forEach(t -> historial.add(OperacionResponse.builder()
                        .id(t.getId())
                        .tipo("TRANSACCION")
                        .simboloAccion(t.getSimboloAccion())
                        .cantidad(t.getCantidad())
                        .precio(t.getPrecio())
                        .fecha(t.getFecha())
                        .estado(null) // transaccion no tiene estado
                        .build()));

        historial.sort(Comparator.comparing(OperacionResponse::getFecha).reversed());

        return historial;
    }
}
