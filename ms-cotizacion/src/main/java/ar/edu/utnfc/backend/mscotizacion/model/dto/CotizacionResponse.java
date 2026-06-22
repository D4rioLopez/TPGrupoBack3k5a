package ar.edu.utnfc.backend.mscotizacion.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CotizacionResponse {
    private String simbolo;
    private Double precioOriginal;
    private String monedaOriginal;
    private Double precioARS;
    private LocalDateTime timestamp;
}
