package ar.edu.utn.frc.back.ms_transaccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frc.back.ms_transaccion.model.*;
import ar.edu.utn.frc.back.ms_transaccion.repository.DetalleOrdenDeVentaRepository;
import ar.edu.utn.frc.back.ms_transaccion.repository.OrdenDeCompraRepository;
import ar.edu.utn.frc.back.ms_transaccion.repository.OrdenDeVentaRepository;
import ar.edu.utn.frc.back.ms_transaccion.repository.TransaccionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenDeCompraService {
    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;
    @Autowired
    private DetalleOrdenDeVentaRepository detalleOrdenDeVentaRepository;
    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private OrdenDeVentaRepository ordenDeVentaRepository;

    @Transactional
    public OrdenDeCompra procesarOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        //busco matcheo de oc con dov
        List<DetalleOrdenDeVenta> detalles = detalleOrdenDeVentaRepository.findDetallesOCCompatibles(
                ordenDeCompra.getSimboloAccion(),
                ordenDeCompra.getCantidad(),
                ordenDeCompra.getPrecioMaximo()
        );

        //rechazo oc
        if (detalles.isEmpty()) {
            ordenDeCompra.setEstado(EstadoOrdenCompra.RECHAZADA);
            return ordenDeCompraRepository.save(ordenDeCompra);
        }
        DetalleOrdenDeVenta detalle = detalles.getFirst();

        //ejecuto oc
        ordenDeCompra.setEstado(EstadoOrdenCompra.EJECUTADA);
        OrdenDeCompra oc = ordenDeCompraRepository.save(ordenDeCompra);

        //creo trnasaccion con oc y dov
        Transaccion transaccion = new Transaccion();
        transaccion.setOrdenDeCompra(oc);
        transaccion.setDetalleOrdenDeVenta(detalle);
        transaccion.setSimboloAccion(ordenDeCompra.getSimboloAccion());
        transaccion.setCantidad(ordenDeCompra.getCantidad());
        transaccion.setPrecio(detalle.getPrecioUnitario());
        transaccion.setFecha(LocalDateTime.now());
        transaccionRepository.save(transaccion);

        //actualizo dov
        detalle.setCantidadDisponible(detalle.getCantidadDisponible() - ordenDeCompra.getCantidad());
        detalleOrdenDeVentaRepository.save(detalle);

        //actualizo ov
        OrdenDeVenta ov = detalle.getOrdenDeVenta();
        if (detalle.getCantidadDisponible() == 0) {
            ov.setEstado(EstadoOrdenVenta.COMPLETA);
        } else {
            ov.setEstado(EstadoOrdenVenta.PARCIAL);
        }
        ordenDeVentaRepository.save(ov);

        return oc;
    }
}