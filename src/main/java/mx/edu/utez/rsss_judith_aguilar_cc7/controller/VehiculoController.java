package mx.edu.utez.rsss_judith_aguilar_cc7.controller;

import jakarta.validation.Valid;
import mx.edu.utez.rsss_judith_aguilar_cc7.controller.dto.CrearVehiculoDTO;
import mx.edu.utez.rsss_judith_aguilar_cc7.model.Vehiculo;
import mx.edu.utez.rsss_judith_aguilar_cc7.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final MyService service;
    public VehiculoController(MyService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Vehiculo> registrar(@Valid @RequestBody CrearVehiculoDTO payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarVehiculo(payload));
    }

}
