package com.Ruben.BackWeb.bus.infrastructure.dto;

import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputBusDTO {
    private final int capacidad = 40;
    private List<Reserva> reservas;
    private String ciudadDestino;
    private Date fechaReserva;
    private Float HoraReserva;
    private String estado = "ACTIVO";

    public OutputBusDTO(Bus bus) {
        setReservas(bus.getReservas());
        setCiudadDestino(bus.getCiudadDestino());
        setFechaReserva(bus.getFechaReserva());
        setHoraReserva(bus.getHoraReserva());
        setEstado(bus.getEstado());
    }
}
