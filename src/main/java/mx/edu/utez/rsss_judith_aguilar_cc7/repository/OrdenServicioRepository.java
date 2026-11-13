package mx.edu.utez.rsss_judith_aguilar_cc7.repository;

import mx.edu.utez.rsss_judith_aguilar_cc7.model.EstadoOrden;
import mx.edu.utez.rsss_judith_aguilar_cc7.model.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {
    boolean existsByVehiculo_IdAndEstadoIn(Long vehiculoId, List<EstadoOrden> estados);
    List<OrdenServicio> findByVehiculo_IdAndEstadoIn(Long vehiculoId, List<EstadoOrden> estados);
}
