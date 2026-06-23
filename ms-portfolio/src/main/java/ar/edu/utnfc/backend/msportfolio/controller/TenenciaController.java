package ar.edu.utnfc.backend.msportfolio.controller;

import ar.edu.utnfc.backend.msportfolio.model.Portfolio;
import ar.edu.utnfc.backend.msportfolio.model.Tenencia;
import ar.edu.utnfc.backend.msportfolio.service.PortfolioService;
import ar.edu.utnfc.backend.msportfolio.service.TenenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenencias")
public class TenenciaController {
    private final TenenciaService tenenciaService;

    public TenenciaController(TenenciaService tenenciaService){
        this.tenenciaService = tenenciaService;

    }
    @GetMapping
    public List<Tenencia> obtenerTodas(){
        return tenenciaService.obtenerTodas();
    }
    @GetMapping("/{id}")
    public Tenencia obterPorId(@PathVariable Long id){
        return tenenciaService.obtenerPorId(id);
    }

    @GetMapping("/usuario/{keycloakId}")
    public List<Tenencia> obterPorKeycloakId(@PathVariable String keycloakId){
        return tenenciaService.obtenerPorKeycloakId(keycloakId);
    }


    @PostMapping
    public Tenencia guardar(@RequestBody Tenencia tenencia){
        return tenenciaService.guardar(tenencia);
    }
    @PutMapping("/{id}")
    public Tenencia actualizar(@PathVariable Long id,
                               @RequestBody Tenencia tenencia){
        return tenenciaService.actualizar(id,tenencia);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        tenenciaService.eliminar(id);
    }

    @PostMapping("/usuario/{keycloakId}/actualizar-tenencia")
    public Tenencia actualizarTenencia(@PathVariable String keycloakId,
                                        @RequestParam String ticker,
                                        @RequestParam Double cantidadDelta) {
        return tenenciaService.actualizarTenencia(keycloakId, ticker, cantidadDelta);
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
}
