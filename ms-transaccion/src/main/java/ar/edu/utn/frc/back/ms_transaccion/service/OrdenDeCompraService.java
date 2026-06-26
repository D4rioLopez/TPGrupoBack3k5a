package ar.edu.utn.frc.back.ms_transaccion.service;

import ar.edu.utn.frc.back.ms_transaccion.client.PortfolioClient;
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

    @Autowired
    private PortfolioClient portfolioClient;

    @Transactional
    public OrdenDeCompra procesarOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        //valido el saldo de portfolio
        Boolean tieneSaldo = portfolioClient.validarSaldo(
                ordenDeCompra.getKeycloakId(),
                ordenDeCompra.getPrecioMaximo() * ordenDeCompra.getCantidad()
        );
        if (Boolean.FALSE.equals(tieneSaldo)) {
            ordenDeCompra.setEstado(EstadoOrdenCompra.RECHAZADA);
            return ordenDeCompraRepository.save(ordenDeCompra);
        }
        //busco matcheo de oc con dov
        List<DetalleOrdenDeVenta> detalles = detalleOrdenDeVentaRepository.findDetallesOCCompatibles(
                ordenDeCompra.getSimboloAccion(),
                ordenDeCompra.getCantidad(),
                ordenDeCompra.getPrecioMaximo()
        ).stream() //valido que el comprador no sea el mismo que el vendedor
                .filter(d -> !d.getOrdenDeVenta().getKeycloakId().equals(ordenDeCompra.getKeycloakId())).toList();

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

        //actualizo el portfolio
        double total = detalle.getPrecioUnitario() * ordenDeCompra.getCantidad();
        String keycloakComprador = ordenDeCompra.getKeycloakId();
        String keycloakVendedor = ov.getKeycloakId();
        String simbolo = ordenDeCompra.getSimboloAccion();
        double cantidad = ordenDeCompra.getCantidad();

        portfolioClient.actualizarSaldo(keycloakComprador, -total);
        portfolioClient.actualizarTenencia(keycloakComprador, simbolo, cantidad);
        portfolioClient.actualizarSaldo(keycloakVendedor, total);
        portfolioClient.actualizarTenencia(keycloakVendedor, simbolo, -cantidad);

        return oc;
    }

    public List<OrdenDeCompra> listarOrdenesDeCompra(String keycloakId, EstadoOrdenCompra estado) {
        if (keycloakId != null && estado != null) {
            return ordenDeCompraRepository.findByKeycloakIdAndEstado(keycloakId, estado);
        } else if (keycloakId != null) {
            return ordenDeCompraRepository.findByKeycloakIdOrderByFechaDesc(keycloakId);
        } else if (estado != null) {
            return ordenDeCompraRepository.findByEstado(estado);
        } else {
            return ordenDeCompraRepository.findAll();
        }
    }
}