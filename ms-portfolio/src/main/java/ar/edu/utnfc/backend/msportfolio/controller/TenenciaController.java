package ar.edu.utnfc.backend.msportfolio.controller;

import ar.edu.utnfc.backend.msportfolio.model.Portfolio;
import ar.edu.utnfc.backend.msportfolio.model.Tenencia;
import ar.edu.utnfc.backend.msportfolio.model.dto.TenenciaRequest;
import ar.edu.utnfc.backend.msportfolio.model.dto.TenenciaResponse;
import ar.edu.utnfc.backend.msportfolio.service.PortfolioService;
import ar.edu.utnfc.backend.msportfolio.service.TenenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenencias")
public class TenenciaController {
    private final TenenciaService tenenciaService;
    private final PortfolioService portfolioService;

    public TenenciaController(TenenciaService tenenciaService, PortfolioService portfolioService){
        this.tenenciaService = tenenciaService;
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public List<TenenciaResponse> obtenerTodas(){
        return tenenciaService.obtenerTodas().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TenenciaResponse obterPorId(@PathVariable Long id){
        return toResponse(tenenciaService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{keycloakId}")
    public List<TenenciaResponse> obterPorKeycloakId(@PathVariable String keycloakId){
        return tenenciaService.obtenerPorKeycloakId(keycloakId).stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public TenenciaResponse guardar(@RequestBody TenenciaRequest request){
        Tenencia tenencia = toEntity(request);
        return toResponse(tenenciaService.guardar(tenencia));
    }

    @PutMapping("/{id}")
    public TenenciaResponse actualizar(@PathVariable Long id,
                               @RequestBody TenenciaRequest request){
        Tenencia tenencia = toEntity(request);
        return toResponse(tenenciaService.actualizar(id, tenencia));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        tenenciaService.eliminar(id);
    }

    @PostMapping("/usuario/{keycloakId}/actualizar-tenencia")
    public TenenciaResponse actualizarTenencia(@PathVariable String keycloakId,
                                        @RequestParam String ticker,
                                        @RequestParam Double cantidadDelta) {
        return toResponse(tenenciaService.actualizarTenencia(keycloakId, ticker, cantidadDelta));
    }

    @GetMapping("/usuario/{keycloakId}/validar")
    public Boolean validarTenencia(@PathVariable String keycloakId,
                                   @RequestParam String ticker,
                                   @RequestParam Double cantidad) {
        try {
            return tenenciaService.obtenerPorKeycloakId(keycloakId).stream()
                    .filter(t -> t.getTicker().equalsIgnoreCase(ticker))
                    .findFirst()
                    .map(t -> t.getCantidad() >= cantidad)
                    .orElse(false);
        } catch (Exception e) {
            return false;
        }
    }

    private TenenciaResponse toResponse(Tenencia tenencia) {
        if (tenencia == null) return null;
        return TenenciaResponse.builder()
                .id(tenencia.getId())
                .ticker(tenencia.getTicker())
                .cantidad(tenencia.getCantidad())
                .portfolioId(tenencia.getPortfolio() != null ? tenencia.getPortfolio().getId() : null)
                .build();
    }

    private Tenencia toEntity(TenenciaRequest request) {
        if (request == null) return null;
        Portfolio portfolio = null;
        if (request.getPortfolioId() != null) {
            portfolio = portfolioService.obtenerPorId(request.getPortfolioId());
        }
        return Tenencia.builder()
                .ticker(request.getTicker())
                .cantidad(request.getCantidad())
                .portfolio(portfolio)
                .build();
    }
}
