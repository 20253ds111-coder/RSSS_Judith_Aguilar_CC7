package mx.edu.utez.rsss_judith_aguilar_cc7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearVehiculoDTO {
    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 20, message = "La placa no debe superar 20 caracteres")
    private String placa;
    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50)
    private String marca;
    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50)
    private String modelo;
    @NotBlank(message = "El propietario es obligatorio")
    @Size(max = 100)
    private String propietario;

}
