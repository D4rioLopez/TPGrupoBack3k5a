package ar.edu.utnfc.backend.msportfolio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenenciaResponse {
    private Long id;
    private String simboloAccion;
    private Double cantidad;
    private Long portfolioId;
}
