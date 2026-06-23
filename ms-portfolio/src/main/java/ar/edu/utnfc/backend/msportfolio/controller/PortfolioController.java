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

    @GetMapping("/{id}")
    public Portfolio obtenerPorId(@PathVariable Long id) {
        return portfolioService.obtenerPorId(id);
    }

    @GetMapping("/usuario/{keycloakId}")
    public Portfolio obterPorKeycloakId(@PathVariable String keycloakId){
        return portfolioService.obtenerPorKeycloakId(keycloakId);
    }

    @PostMapping
    public Portfolio guardar(@RequestBody Portfolio portfolio) {
        return portfolioService.guardar(portfolio);
    }

    @PutMapping("/{id}")
    public Portfolio actualizar(
            @PathVariable Long id,
            @RequestBody Portfolio portfolio) {

        return portfolioService.actualizar(id, portfolio);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        portfolioService.eliminar(id);
    }

    @PostMapping("/usuario/{keycloakId}/cargar-saldo")
    public Portfolio cargarSaldo(@PathVariable String keycloakId, @RequestParam Double monto) {
        return portfolioService.ingresarSaldo(keycloakId, monto);
    }

    @PutMapping("/usuario/{keycloakId}/actualizar-saldo")
    public Portfolio actualizarSaldo(@PathVariable String keycloakId, @RequestParam Double montoDelta) {
        return portfolioService.actualizarSaldo(keycloakId, montoDelta);
    }

    @GetMapping("/usuario/{keycloakId}/validar-saldo")
    public Boolean validarSaldo(@PathVariable String keycloakId, @RequestParam Double monto) {
        try {
            Portfolio portfolio = portfolioService.obtenerPorKeycloakId(keycloakId);
            return portfolio.getSaldo() >= monto;
        } catch (Exception e) {
            return false;
        }
    }
}
