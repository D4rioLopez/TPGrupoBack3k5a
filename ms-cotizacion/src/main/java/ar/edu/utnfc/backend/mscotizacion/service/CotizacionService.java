package ar.edu.utnfc.backend.mscotizacion.service;

import ar.edu.utnfc.backend.mscotizacion.model.dto.CotizacionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CotizacionService {

    private final RestTemplate restTemplate;
    
    // APIs públicas keyless
    private static final String YAHOO_FINANCE_URL = "https://query1.finance.yahoo.com/v8/finance/chart/";
    private static final String EXCHANGE_RATE_URL = "https://open.er-api.com/v6/latest/USD";

    // Valores de respaldo por si falla la conexión a internet
    private static final double DEFAULT_ARS_RATE = 1400.0;
    private static final Map<String, Double> MOCK_PRICES_USD = new HashMap<>();

    static {
        MOCK_PRICES_USD.put("AAPL", 185.50);
        MOCK_PRICES_USD.put("NVDA", 950.00);
        MOCK_PRICES_USD.put("GGAL", 32.20);
        MOCK_PRICES_USD.put("MSFT", 425.10);
        MOCK_PRICES_USD.put("TSLA", 178.40);
        MOCK_PRICES_USD.put("YPF", 18.50);
    }

    public CotizacionService() {
        this.restTemplate = new RestTemplate();
    }

    public CotizacionResponse obtenerCotizacion(String simbolo) {
        String symbolUpper = simbolo.toUpperCase();
        
        Double precioOriginal = null;
        String monedaOriginal = "USD";
        Double tipoCambio = null;

        // 1. Intentar consultar Yahoo Finance para obtener el precio de la acción
        try {
            String url = YAHOO_FINANCE_URL + symbolUpper;
            JsonNode root = restTemplate.getForObject(url, JsonNode.class);
            if (root != null && root.has("chart") && root.path("chart").has("result")) {
                JsonNode result = root.path("chart").path("result").get(0);
                if (result != null) {
                    precioOriginal = result.path("meta").path("regularMarketPrice").asDouble();
                    monedaOriginal = result.path("meta").path("currency").asText("USD");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al conectar con Yahoo Finance, usando precio de respaldo para " + symbolUpper + ": " + e.getMessage());
        }

        // Si falló Yahoo Finance (u offline), usar los valores mockeados de respaldo
        if (precioOriginal == null) {
            precioOriginal = MOCK_PRICES_USD.getOrDefault(symbolUpper, 100.00);
            monedaOriginal = "USD";
        }

        // 2. Intentar consultar la API de Tipo de Cambio para obtener USD -> ARS
        try {
            JsonNode rateRoot = restTemplate.getForObject(EXCHANGE_RATE_URL, JsonNode.class);
            if (rateRoot != null && rateRoot.has("rates")) {
                tipoCambio = rateRoot.path("rates").path("ARS").asDouble();
            }
        } catch (Exception e) {
            System.err.println("Error al conectar con la API de tipo de cambio, usando valor por defecto ($1000 ARS): " + e.getMessage());
        }

        if (tipoCambio == null) {
            tipoCambio = DEFAULT_ARS_RATE;
        }

        // 3. Calcular precio final en ARS
        double precioARS;
        if ("ARS".equals(monedaOriginal)) {
            precioARS = precioOriginal;
        } else {
            // Asumimos que cotiza en USD, por lo tanto hacemos la conversión
            precioARS = precioOriginal * tipoCambio;
        }

        return CotizacionResponse.builder()
                .simbolo(symbolUpper)
                .precioOriginal(precioOriginal)
                .monedaOriginal(monedaOriginal)
                .precioARS(Math.round(precioARS * 100.0) / 100.0) // Redondear a 2 decimales
                .timestamp(LocalDateTime.now())
                .build();
    }
}
