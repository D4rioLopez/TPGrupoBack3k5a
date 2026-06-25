package ar.edu.utnfc.backend.mscotizacion.controller;

import ar.edu.utnfc.backend.mscotizacion.model.dto.CotizacionResponse;
import ar.edu.utnfc.backend.mscotizacion.service.CotizacionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionController {

    private final CotizacionService cotizacionService;

    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    @GetMapping("/{simbolo}")
    public CotizacionResponse obtenerCotizacion(@PathVariable String simbolo) {
        return cotizacionService.obtenerCotizacion(simbolo);
    }
}
