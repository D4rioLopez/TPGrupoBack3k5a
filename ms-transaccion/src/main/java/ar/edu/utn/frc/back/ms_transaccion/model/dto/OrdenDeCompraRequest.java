package ar.edu.utn.frc.back.ms_transaccion.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrdenDeCompraRequest {
    @NotBlank(message = "El símbolo de acción no puede estar vacío")
    private String simboloAccion;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    private Long cantidad;

    @NotNull(message = "El precio máximo es obligatorio")
    @Min(value = 1, message = "El precio mínimo debe ser mayor a 0")
    private Double precioMaximo;

    private String estado;
}
