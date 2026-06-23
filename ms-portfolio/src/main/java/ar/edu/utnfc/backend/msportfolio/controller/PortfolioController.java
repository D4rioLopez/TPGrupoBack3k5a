package ar.edu.utnfc.backend.msportfolio.controller;

import ar.edu.utnfc.backend.msportfolio.model.Portfolio;
import ar.edu.utnfc.backend.msportfolio.model.dto.PortfolioRequest;
import ar.edu.utnfc.backend.msportfolio.model.dto.PortfolioResponse;
import ar.edu.utnfc.backend.msportfolio.model.dto.TenenciaResponse;
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
    public List<PortfolioResponse> obtenerTodos() {
        return portfolioService.obtenerTodos().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PortfolioResponse obtenerPorId(@PathVariable Long id) {
        return toResponse(portfolioService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{keycloakId}")
    public PortfolioResponse obterPorKeycloakId(@PathVariable String keycloakId){
        return toResponse(portfolioService.obtenerPorKeycloakId(keycloakId));
    }

    @PostMapping
    public PortfolioResponse guardar(@RequestBody PortfolioRequest request) {
        Portfolio portfolio = toEntity(request);
        return toResponse(portfolioService.guardar(portfolio));
    }

    @PutMapping("/{id}")
    public PortfolioResponse actualizar(
            @PathVariable Long id,
            @RequestBody PortfolioRequest request) {
        Portfolio portfolio = toEntity(request);
        return toResponse(portfolioService.actualizar(id, portfolio));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        portfolioService.eliminar(id);
    }

    @PostMapping("/usuario/{keycloakId}/cargar-saldo")
    public PortfolioResponse cargarSaldo(@PathVariable String keycloakId, @RequestParam Double monto) {
        return toResponse(portfolioService.ingresarSaldo(keycloakId, monto));
    }

    @PutMapping("/usuario/{keycloakId}/actualizar-saldo")
    public PortfolioResponse actualizarSaldo(@PathVariable String keycloakId, @RequestParam Double montoDelta) {
        return toResponse(portfolioService.actualizarSaldo(keycloakId, montoDelta));
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

    private PortfolioResponse toResponse(Portfolio portfolio) {
        if (portfolio == null) return null;
        List<TenenciaResponse> tenenciasResponse = null;
        if (portfolio.getTenencias() != null) {
            tenenciasResponse = portfolio.getTenencias().stream()
                    .map(t -> TenenciaResponse.builder()
                            .id(t.getId())
                            .ticker(t.getTicker())
                            .cantidad(t.getCantidad())
                            .portfolioId(portfolio.getId())
                            .build())
                    .toList();
        }
        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .keycloakId(portfolio.getKeycloakId())
                .nombre(portfolio.getNombre())
                .saldo(portfolio.getSaldo())
                .tenencias(tenenciasResponse)
                .build();
    }

    private Portfolio toEntity(PortfolioRequest request) {
        if (request == null) return null;
        return Portfolio.builder()
                .keycloakId(request.getKeycloakId())
                .nombre(request.getNombre())
                .saldo(request.getSaldo() != null ? request.getSaldo() : 0.0)
                .build();
    }
}
