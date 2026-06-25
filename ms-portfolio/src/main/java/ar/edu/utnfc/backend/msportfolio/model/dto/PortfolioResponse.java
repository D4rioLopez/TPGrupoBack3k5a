package ar.edu.utnfc.backend.msportfolio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioResponse {
    private Long id;
    private String keycloakId;
    private String nombre;
    private Double saldo;
    private List<TenenciaResponse> tenencias;
}
