package ar.edu.utn.frc.back.ms_transaccion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ORDENES_DE_VENTA")
public class OrdenDeVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "ordenDeVenta", cascade = CascadeType.ALL)
    private List<DetalleOrdenDeVenta> detalleOrdenDeVenta;

    @Enumerated(EnumType.STRING)
    private EstadoOrdenVenta estado;
}
