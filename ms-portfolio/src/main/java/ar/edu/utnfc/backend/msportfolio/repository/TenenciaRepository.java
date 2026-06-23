package ar.edu.utnfc.backend.msportfolio.repository;

import ar.edu.utnfc.backend.msportfolio.model.Tenencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TenenciaRepository extends JpaRepository<Tenencia, Long>{

    List<Tenencia> findByPortfolioKeycloakId(String keycloakId);

    Optional<Tenencia> findByPortfolioKeycloakIdAndTicker(String keycloakId, String ticker);
}
