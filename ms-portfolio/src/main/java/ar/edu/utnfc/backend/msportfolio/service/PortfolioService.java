package ar.edu.utnfc.backend.msportfolio.service;

import ar.edu.utnfc.backend.msportfolio.model.Portfolio;
import ar.edu.utnfc.backend.msportfolio.repository.PortfolioRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public List<Portfolio> obtenerTodos() {
        return portfolioRepository.findAll();
    }

    public Portfolio obtenerPorId(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio no encontrado"));
    }

    public Portfolio guardar(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    public Portfolio actualizar(Long id, Portfolio portfolioActualizado) {

        Portfolio portfolio = obtenerPorId(id);

        portfolio.setNombre(portfolioActualizado.getNombre());
        portfolio.setSaldo(portfolioActualizado.getSaldo());
        portfolio.setKeycloakId(portfolioActualizado.getKeycloakId());
        portfolio.setSaldo(portfolioActualizado.getSaldo());

        return portfolioRepository.save(portfolio);
    }

    public void eliminar(Long id) {

        Portfolio portfolio = obtenerPorId(id);

        portfolioRepository.delete(portfolio);
    }

    public Portfolio obtenerPorKeycloakId(String keycloakId) {
        return portfolioRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("Portfolio no encontrado para el usuario"));
    }

    public Portfolio ingresarSaldo(String keycloakId, Double monto) {
        if (monto == null || monto <= 0) {
            throw new IllegalArgumentException("El monto a ingresar debe ser mayor a cero");
        }
        Portfolio portfolio = obtenerPorKeycloakId(keycloakId);
        portfolio.setSaldo(portfolio.getSaldo() + monto);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio actualizarSaldo(String keycloakId, Double montoDelta) {
        if (montoDelta == null) {
            throw new IllegalArgumentException("El monto delta no puede ser nulo");
        }
        Portfolio portfolio = obtenerPorKeycloakId(keycloakId);
        double nuevoSaldo = portfolio.getSaldo() + montoDelta;
        if (nuevoSaldo < 0) {
            throw new RuntimeException("Saldo insuficiente en el portfolio");
        }
        portfolio.setSaldo(nuevoSaldo);
        return portfolioRepository.save(portfolio);
    }
}