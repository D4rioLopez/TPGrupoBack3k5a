package ar.edu.utn.frc.back.ms_transaccion.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class PortfolioClient {
    private RestTemplate restTemplate;
    private String portfolioUrl;

    public PortfolioClient(RestTemplate restTemplate,
                           @Value("${portfolio.service.url}") String portfolioUrl) {
        this.restTemplate = restTemplate;
        this.portfolioUrl = portfolioUrl;
    }

    public Boolean validarSaldo(String keycloakId, Double monto) {
        String url = UriComponentsBuilder
                .fromUriString(portfolioUrl + "/api/portfolios/usuario/{id}/validar-saldo")
                .queryParam("monto", monto)
                .buildAndExpand(keycloakId).toUriString();
        return restTemplate.getForObject(url, Boolean.class);
    }

    public Boolean validarTenencia(String keycloakId, String simboloAccion, Long cantidad) {
        String url = UriComponentsBuilder
                .fromUriString(portfolioUrl + "/api/tenencias/usuario/{id}/validar")
                .queryParam("simboloAccion", simboloAccion)
                .queryParam("cantidad", cantidad)
                .buildAndExpand(keycloakId).toUriString();
        return restTemplate.getForObject(url, Boolean.class);
    }

    public void actualizarSaldo(String keycloakId, Double monto) {
        String url = UriComponentsBuilder
                .fromUriString(portfolioUrl + "/api/portfolios/usuario/{id}/actualizar-saldo")
                .queryParam("monto", monto)
                .buildAndExpand(keycloakId).toUriString();
        restTemplate.put(url, null);
    }

    public void actualizarTenencia(String keycloakId, String simboloAccion, Double cantidad) {
        String url = UriComponentsBuilder
                .fromUriString(portfolioUrl + "/api/tenencias/usuario/{id}/actualizar-tenencia")
                .queryParam("simboloAccion", simboloAccion)
                .queryParam("cantidad", cantidad)
                .buildAndExpand(keycloakId).toUriString();
        restTemplate.postForObject(url, null, Object.class);
    } 
}
