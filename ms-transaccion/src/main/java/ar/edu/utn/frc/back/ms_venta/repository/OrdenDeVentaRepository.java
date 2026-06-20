package ar.edu.utn.frc.back.ms_venta.repository;

import ar.edu.utn.frc.back.ms_venta.model.OrdenDeVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDeVentaRepository extends JpaRepository<OrdenDeVenta, Long> {
}
