package ar.edu.utn.frc.back.ms_transaccion.service;

import ar.edu.utn.frc.back.ms_transaccion.client.PortfolioClient;
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
    private PortfolioClient portfolioClient;

    //RF4 registrar ov
    public OrdenDeVenta crearOrdenDeVenta(OrdenDeVenta ordenDeVenta) {
        for (DetalleOrdenDeVenta detalle : ordenDeVenta.getDetalleOrdenDeVenta()) {
            Boolean tieneStock = portfolioClient.validarTenencia(
                    ordenDeVenta.getKeycloakId(),
                    detalle.getSimboloAccion(),
                    detalle.getCantidad()
            );
            if (Boolean.FALSE.equals(tieneStock)) {
                throw new RuntimeException("Tenencias insuficientes para " + detalle.getSimboloAccion());
            }
        }

        ordenDeVenta.setEstado(EstadoOrdenVenta.ACTIVA);
        ordenDeVenta.setFecha(LocalDateTime.now());

        for (DetalleOrdenDeVenta detalle :  ordenDeVenta.getDetalleOrdenDeVenta()) {
            detalle.setCantidadDisponible(detalle.getCantidad());
            detalle.setOrdenDeVenta(ordenDeVenta);
        }

        return ordenDeVentaRepository.save(ordenDeVenta);
    }
}
