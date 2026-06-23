package ar.edu.utnfc.backend.msportfolio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioRequest {
    private String keycloakId;
    private String nombre;
    private Double saldo;
}
