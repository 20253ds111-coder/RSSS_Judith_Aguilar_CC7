package mx.edu.utez.rsss_judith_aguilar_cc7.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarCostoDTO {
    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.00", message = "El costo debe ser mayor o igual a cero")
    private BigDecimal costo;


}
