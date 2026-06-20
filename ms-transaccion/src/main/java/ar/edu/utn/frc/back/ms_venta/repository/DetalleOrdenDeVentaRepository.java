package ar.edu.utn.frc.back.ms_venta.repository;

import ar.edu.utn.frc.back.ms_venta.model.DetalleOrdenDeVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleOrdenDeVentaRepository extends JpaRepository<DetalleOrdenDeVenta, Long> {
    List<DetalleOrdenDeVenta> findBySimboloAccion(String simboloAccion);
}
