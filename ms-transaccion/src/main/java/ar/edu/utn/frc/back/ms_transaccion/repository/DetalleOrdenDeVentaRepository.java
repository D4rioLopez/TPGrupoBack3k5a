package ar.edu.utn.frc.back.ms_transaccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.back.ms_transaccion.model.DetalleOrdenDeVenta;

import java.util.List;

@Repository
public interface DetalleOrdenDeVentaRepository extends JpaRepository<DetalleOrdenDeVenta, Long> {
    @Query("select dov from DetalleOrdenDeVenta dov where dov.simboloAccion = :simbolo and " +
            "dov.cantidadDisponible >= :cantidad and " +
            "dov.precioUnitario <= :precioMaximo " +
            "order by dov.precioUnitario asc")
    List<DetalleOrdenDeVenta> findDetallesOCCompatibles(@Param("simbolo") String simbolo, @Param("cantidad") Long cantidad, @Param("precioMaximo") Double precioMaximo);
}
