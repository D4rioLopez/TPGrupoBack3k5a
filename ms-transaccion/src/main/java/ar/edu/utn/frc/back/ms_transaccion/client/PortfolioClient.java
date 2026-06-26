package ar.edu.utn.frc.back.ms_transaccion.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import ar.edu.utn.frc.back.ms_transaccion.exception.ServiceUnavailableException;
import org.springframework.web.client.RestClientException;

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
        try {
            return restTemplate.getForObject(url, Boolean.class);
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("El microservicio ms-portfolio no está disponible o respondió con un error de red");
        }
    }

    public Boolean validarTenencia(String keycloakId, String simboloAccion, Long cantidad) {
        String url = UriComponentsBuilder
                .fromUriString(portfolioUrl + "/api/tenencias/usuario/{id}/validar")
                .queryParam("simboloAccion", simboloAccion)
                .queryParam("cantidad", cantidad)
                .buildAndExpand(keycloakId).toUriString();
        try {
            return restTemplate.getForObject(url, Boolean.class);
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("El microservicio ms-portfolio no está disponible o respondió con un error de red");
        }
    }

    public void actualizarSaldo(String keycloakId, Double monto) {
        String url = UriComponentsBuilder
                .fromUriString(portfolioUrl + "/api/portfolios/usuario/{id}/actualizar-saldo")
                .queryParam("monto", monto)
                .buildAndExpand(keycloakId).toUriString();
        try {
            restTemplate.put(url, null);
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("El microservicio ms-portfolio no está disponible o respondió con un error de red");
        }
    }

    public void actualizarTenencia(String keycloakId, String simboloAccion, Double cantidad) {
        String url = UriComponentsBuilder
                .fromUriString(portfolioUrl + "/api/tenencias/usuario/{id}/actualizar-tenencia")
                .queryParam("simboloAccion", simboloAccion)
                .queryParam("cantidad", cantidad)
                .buildAndExpand(keycloakId).toUriString();
        try {
            restTemplate.postForObject(url, null, Object.class);
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("El microservicio ms-portfolio no está disponible o respondió con un error de red");
        }
    } 
}
