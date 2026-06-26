package ar.edu.utn.frc.back.ms_transaccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.back.ms_transaccion.model.OrdenDeVenta;

@Repository
public interface OrdenDeVentaRepository extends JpaRepository<OrdenDeVenta, Long> {
    List<OrdenDeVenta> findByKeycloakIdOrderByFechaDesc(String keycloakId);
    //aca resuelvo parte de RF 5, busco la operacion (OV) con su fecha en descendente.
}
