package ar.edu.utnfc.backend.msportfolio.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CotizacionClient {
    private RestTemplate restTemplate;
    private String cotizacionUrl;

    public CotizacionClient(RestTemplate restTemplate,
                            @Value("${cotizacion.service.url}") String cotizacionUrl) {
        this.restTemplate = restTemplate;
        this.cotizacionUrl = cotizacionUrl;
    }

    public Double obtenerPrecioARS(String simboloAccion) {
        String url = cotizacionUrl + "/api/cotizaciones/" + simboloAccion;
        Map response = restTemplate.getForObject(url, Map.class);
        if (response == null) return 0.0;
        return ((Number) response.get("precioARS")).doubleValue();
    }
}
