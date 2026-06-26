package ar.edu.utn.frc.back.ms_transaccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.back.ms_transaccion.model.Transaccion;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByOrderByFechaDesc();
    //resuelvo RF 6
    @Query(
            "select t from Transaccion t " +
                    "where t.detalleOrdenDeVenta.ordenDeVenta.keycloakId = :keycloakId or " +
                    "t.ordenDeCompra.keycloakId = :keycloakId " +
                    "order by t.fecha desc"
    )
    List<Transaccion> findHistorialBykeycloakId(@Param("keycloakId") String keycloakId);
    //resuelvo parte de RF 5, busco la operacion Transaccion con su fecha ne descendente.
}
