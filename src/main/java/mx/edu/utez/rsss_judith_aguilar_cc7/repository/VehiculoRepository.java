package mx.edu.utez.rsss_judith_aguilar_cc7.repository;

import mx.edu.utez.rsss_judith_aguilar_cc7.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    boolean existsByPlacaIgnoreCase(String placa);
}

