package ar.edu.utnfc.backend.msportfolio.service;

import ar.edu.utnfc.backend.msportfolio.model.Portfolio;
import ar.edu.utnfc.backend.msportfolio.model.Tenencia;
import ar.edu.utnfc.backend.msportfolio.repository.PortfolioRepository;
import ar.edu.utnfc.backend.msportfolio.repository.TenenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenenciaService {
    private final TenenciaRepository tenenciaRepository;
    private final PortfolioRepository portfolioRepository;

    public TenenciaService(TenenciaRepository tenenciaRepository, PortfolioRepository portfolioRepository) {
        this.tenenciaRepository = tenenciaRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public List<Tenencia> obtenerTodas() {
        return tenenciaRepository.findAll();
    }

    public List<Tenencia> obtenerPorKeycloakId(String keycloakId) {
        return tenenciaRepository.findByPortfolioKeycloakId(keycloakId);
    }

    public Tenencia obtenerPorId(Long id) {
        return tenenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenencia no encontrada"));
    }

    public Tenencia guardar(Tenencia tenencia) {
        return tenenciaRepository.save(tenencia);
    }

    public Tenencia actualizar(Long id, Tenencia tenenciaActualizada) {

        Tenencia tenencia = obtenerPorId(id);
        tenencia.setTicker(tenenciaActualizada.getTicker());
        tenencia.setCantidad(tenenciaActualizada.getCantidad());
        tenencia.setPortfolio(tenenciaActualizada.getPortfolio());

        return tenenciaRepository.save(tenencia);
    }

    public void eliminar(Long id) {

        Tenencia tenencia = obtenerPorId(id);

        tenenciaRepository.delete(tenencia);
    }

    public Tenencia actualizarTenencia(String keycloakId, String ticker, Double cantidadDelta) {
        if (cantidadDelta == null) {
            throw new IllegalArgumentException("La cantidad delta no puede ser nula");
        }

        Optional<Tenencia> tenenciaOpt = tenenciaRepository.findByPortfolioKeycloakIdAndTicker(keycloakId, ticker);

        if (tenenciaOpt.isPresent()) {
            Tenencia tenencia = tenenciaOpt.get();
            double nuevaCantidad = tenencia.getCantidad() + cantidadDelta;
            if (nuevaCantidad < 0) {
                throw new RuntimeException("Cantidad insuficiente de la acción " + ticker + " para realizar la venta");
            } else if (nuevaCantidad == 0) {
                tenenciaRepository.delete(tenencia);
                return null;
            } else {
                tenencia.setCantidad(nuevaCantidad);
                return tenenciaRepository.save(tenencia);
            }
        } else {
            if (cantidadDelta < 0) {
                throw new RuntimeException("No posee tenencias de la acción " + ticker + " para vender");
            }
            Portfolio portfolio = portfolioRepository.findByKeycloakId(keycloakId)
                    .orElseThrow(() -> new RuntimeException("Portfolio no encontrado para el usuario: " + keycloakId));

            Tenencia nuevaTenencia = Tenencia.builder()
                    .ticker(ticker)
                    .cantidad(cantidadDelta)
                    .portfolio(portfolio)
                    .build();
            return tenenciaRepository.save(nuevaTenencia);
        }
    }
}
