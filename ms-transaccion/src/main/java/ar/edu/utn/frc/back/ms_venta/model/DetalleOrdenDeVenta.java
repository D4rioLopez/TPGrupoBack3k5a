package ar.edu.utn.frc.back.ms_venta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="DETALLES_ORDEN_VENTA")
public class DetalleOrdenDeVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String simboloAccion;

    private Integer cantidad;

    private Integer cantidadDisponible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDEN_VENTA_ID", nullable = false)
    private OrdenDeVenta ordenDeVenta;
}
