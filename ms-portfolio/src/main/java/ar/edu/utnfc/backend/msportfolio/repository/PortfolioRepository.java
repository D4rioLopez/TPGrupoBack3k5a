package ar.edu.utnfc.backend.msportfolio.repository;

import ar.edu.utnfc.backend.msportfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByKeycloakId(String keycloakId);

}
