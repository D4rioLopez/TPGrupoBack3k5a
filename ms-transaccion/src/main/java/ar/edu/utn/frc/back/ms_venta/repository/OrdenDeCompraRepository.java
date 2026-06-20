package ar.edu.utn.frc.back.ms_venta.repository;

import ar.edu.utn.frc.back.ms_venta.model.OrdenDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Long> {
    Optional<OrdenDeCompra> findBySimboloAccion(String simboloAccion);
}
