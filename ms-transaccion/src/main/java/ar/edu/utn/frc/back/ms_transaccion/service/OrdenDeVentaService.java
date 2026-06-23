package ar.edu.utn.frc.back.ms_transaccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.back.ms_transaccion.model.DetalleOrdenDeVenta;
import ar.edu.utn.frc.back.ms_transaccion.model.EstadoOrdenVenta;
import ar.edu.utn.frc.back.ms_transaccion.model.OrdenDeVenta;
import ar.edu.utn.frc.back.ms_transaccion.repository.OrdenDeVentaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenDeVentaService {
    @Autowired
    private OrdenDeVentaRepository ordenDeVentaRepository;
    @Autowired

    //RF4 registrar ov
    public OrdenDeVenta crearOrdenDeVenta(OrdenDeVenta ordenDeVenta) {
        ordenDeVenta.setEstado(EstadoOrdenVenta.ACTIVA);
        ordenDeVenta.setFecha(LocalDateTime.now());

        for (DetalleOrdenDeVenta detalle :  ordenDeVenta.getDetalleOrdenDeVenta()) {
            detalle.setCantidadDisponible(detalle.getCantidad());
            detalle.setOrdenDeVenta(ordenDeVenta);
        }

        return ordenDeVentaRepository.save(ordenDeVenta);
    }
}
