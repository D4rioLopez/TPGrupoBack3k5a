package ar.edu.utn.frc.back.ms_transaccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.back.ms_transaccion.model.OrdenDeCompra;

import java.util.List;

@Repository
public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Long> {
    List<OrdenDeCompra> findByKeycloakIdOrderByFechaDesc(String keycloakId);
    //aca resuelvo parte de RF 5, busco la operacion (OC) con su fecha en descendente.
}
