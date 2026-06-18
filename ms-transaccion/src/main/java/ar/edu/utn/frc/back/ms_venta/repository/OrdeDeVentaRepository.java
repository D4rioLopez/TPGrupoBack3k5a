package ar.edu.utn.frc.back.ms_venta.repository;

import ar.edu.utn.frc.back.ms_venta.model.DetalleOrdenDeVenta;
import ar.edu.utn.frc.back.ms_venta.model.OrdenDeVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdeDeVentaRepository extends JpaRepository<DetalleOrdenDeVenta, Long> {
    Optional<OrdenDeVenta> findBySimboloAccion(String simboloAccion);
}
