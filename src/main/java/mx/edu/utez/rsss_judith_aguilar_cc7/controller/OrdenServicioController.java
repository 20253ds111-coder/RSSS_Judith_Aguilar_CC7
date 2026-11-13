package mx.edu.utez.rsss_judith_aguilar_cc7.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import mx.edu.utez.rsss_judith_aguilar_cc7.controller.dto.ActualizarCostoDTO;
import mx.edu.utez.rsss_judith_aguilar_cc7.controller.dto.CrearOrdenDTO;
import mx.edu.utez.rsss_judith_aguilar_cc7.exception.CustomException;
import mx.edu.utez.rsss_judith_aguilar_cc7.model.EstadoOrden;
import mx.edu.utez.rsss_judith_aguilar_cc7.model.OrdenServicio;
import mx.edu.utez.rsss_judith_aguilar_cc7.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@Validated
public class OrdenServicioController {
    private final MyService service;
    public OrdenServicioController(MyService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<OrdenServicio> crear(@Valid @RequestBody CrearOrdenDTO payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearOrdenServicio(payload));
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<OrdenServicio> finalizar(
            @PathVariable Long id, @Valid @RequestBody ActualizarCostoDTO payload) {
        if (payload.getCosto() == null || payload.getCosto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException(
                    "El costo final debe ser mayor a cero");
        }
        return ResponseEntity.ok(service.finalizarOrden(id, payload.getCosto()));
    }

    @PatchMapping("/{id}/costo")
    public ResponseEntity<OrdenServicio> actualizarCosto(
            @PathVariable Long id, @Valid @RequestBody ActualizarCostoDTO payload) {
        return ResponseEntity.ok(service.actualizarCosto(id, payload.getCosto()));
    }

    @PatchMapping("/vehiculo/{idVehiculo}/cancelar-pendientes")
    public ResponseEntity<List<OrdenServicio>> cancelarPendientes(@PathVariable Long idVehiculo) {
        return ResponseEntity.ok(service.cancelarOrdenesPendientes(idVehiculo));
    }

}
