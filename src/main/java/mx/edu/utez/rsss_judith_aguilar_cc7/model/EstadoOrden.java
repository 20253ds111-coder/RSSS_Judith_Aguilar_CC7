package mx.edu.utez.rsss_judith_aguilar_cc7.model;

import java.util.List;

public enum EstadoOrden {
    RECIBIDA,
    EN_REPARACION,
    FINALIZADA,
    CANCELADA;

    public static final List<EstadoOrden> PENDIENTES = List.of(RECIBIDA, EN_REPARACION);
}
