package ar.edu.utn.frc.back.ms_transaccion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ORDENES_DE_COMPRA")
public class OrdenDeCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private Long usuarioId;
    private Double precioMaximo;
    private String simboloAccion;
    private Long cantidad;

    @Enumerated(EnumType.STRING)
    private EstadoOrdenCompra estado;
}
