package mx.edu.utez.rsss_judith_aguilar_cc7.service;

import mx.edu.utez.rsss_judith_aguilar_cc7.controller.dto.*;
import mx.edu.utez.rsss_judith_aguilar_cc7.exception.CustomException;
import mx.edu.utez.rsss_judith_aguilar_cc7.model.*;
import mx.edu.utez.rsss_judith_aguilar_cc7.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MyService {
    private final VehiculoRepository vehiculoRepository;
    private final OrdenServicioRepository ordenServicioRepository;

    public MyService(VehiculoRepository vehiculoRepository, OrdenServicioRepository ordenServicioRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.ordenServicioRepository = ordenServicioRepository;
    }

    public Vehiculo registrarVehiculo(CrearVehiculoDTO payload) {
        String placa = payload.getPlaca().trim();
        if (vehiculoRepository.existsByPlacaIgnoreCase(placa)) {
            throw new CustomException("Ya existe un vehículo registrado con esa placa");
        }
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(placa.toUpperCase());
        vehiculo.setMarca(payload.getMarca().trim());
        vehiculo.setModelo(payload.getModelo().trim());
        vehiculo.setPropietario(payload.getPropietario().trim());
        vehiculo.setActivo(true);
        return vehiculoRepository.save(vehiculo);
    }


    @Transactional
    public OrdenServicio crearOrdenServicio(CrearOrdenDTO payload) {
        Vehiculo vehiculo = vehiculoRepository.findById(payload.getIdVehiculo())
                .orElseThrow(() -> new CustomException("El vehículo no existe"));
        if (!vehiculo.isActivo()) {
            throw new CustomException("No se puede crear una orden para un vehículo inactivo");
        }
        if (ordenServicioRepository.existsByVehiculo_IdAndEstadoIn(vehiculo.getId(), EstadoOrden.PENDIENTES)) {
            throw new CustomException("El vehículo ya tiene una orden recibida o en reparación");
        }
        OrdenServicio orden = new OrdenServicio();
        orden.setDescripcion(payload.getDescripcion().trim());
        orden.setTipo(payload.getTipo());
        orden.setEstado(EstadoOrden.RECIBIDA);
        orden.setCosto(BigDecimal.ZERO);
        orden.setVehiculo(vehiculo);
        return ordenServicioRepository.save(orden);
    }

    @Transactional
    public OrdenServicio finalizarOrden(Long idOrden, BigDecimal costo) {
        OrdenServicio orden = obtenerOrden(idOrden);
        if (orden.getEstado() != EstadoOrden.EN_REPARACION) {
            throw new CustomException("Solo se pueden finalizar órdenes que estén EN_REPARACION");
        }
        if (costo == null || costo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CustomException("El costo final debe ser mayor a cero");
        }
        orden.setCosto(costo);
        orden.setEstado(EstadoOrden.FINALIZADA);
        return ordenServicioRepository.save(orden);
    }

    @Transactional
    public OrdenServicio actualizarCosto(Long idOrden, BigDecimal nuevoCosto) {
        OrdenServicio orden = obtenerOrden(idOrden);
        if (!EstadoOrden.PENDIENTES.contains(orden.getEstado())) {
            throw new CustomException("El costo solo puede modificarse en órdenes RECIBIDA o EN_REPARACION");
        }
        if (nuevoCosto == null || nuevoCosto.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException("El costo debe ser mayor o igual a cero");
        }
        orden.setCosto(nuevoCosto);
        return ordenServicioRepository.save(orden);
    }

    @Transactional
    public List<OrdenServicio> cancelarOrdenesPendientes(Long idVehiculo) {
        vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new CustomException("El vehículo no existe"));
        List<OrdenServicio> pendientes = ordenServicioRepository
                .findByVehiculo_IdAndEstadoIn(idVehiculo, EstadoOrden.PENDIENTES);
        pendientes.forEach(orden -> orden.setEstado(EstadoOrden.CANCELADA));
        return ordenServicioRepository.saveAll(pendientes);
    }

    private OrdenServicio obtenerOrden(Long idOrden) {
        return ordenServicioRepository.findById(idOrden)
                .orElseThrow(() -> new CustomException("La orden de servicio no existe"));
    }
}
