package mx.edu.utez.rsss_judith_aguilar_cc7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.rsss_judith_aguilar_cc7.model.TipoServicio;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearOrdenDTO {
    @NotNull(message = "El ID del vehículo es obligatorio")
    @Positive(message = "El ID del vehículo debe ser positivo")
    private Long idVehiculo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no debe superar 500 caracteres")
    private String descripcion;

    @NotNull(message = "El tipo de servicio es obligatorio")
    private TipoServicio tipo;

}