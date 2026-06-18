package ar.edu.utn.frc.back.ms_venta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "TRANSACCIONES")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long compradorId;

    private Long vendedorId;

    private String simboloAccion;

    private Integer cantidad;

    private Double precio;

    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDEN_VENTA_ID", nullable = false)
    private DetalleOrdenDeVenta detalleOrdenDeVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDEN_COMPRA_ID", nullable = false)
    private OrdenDeCompra ordenDeCompra;
}
