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

    public Portfolio guardar(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }
}
