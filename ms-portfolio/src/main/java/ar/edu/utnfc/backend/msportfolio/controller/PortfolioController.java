package ar.edu.utnfc.backend.msportfolio.controller;

import ar.edu.utnfc.backend.msportfolio.model.Portfolio;
import ar.edu.utnfc.backend.msportfolio.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }
    @GetMapping
    public List<Portfolio> obtenerTodos() {
        return portfolioService.obtenerTodos();
    }

    @PostMapping
    public Portfolio guardar(@RequestBody Portfolio portfolio) {
        return portfolioService.guardar(portfolio);
    }


}
