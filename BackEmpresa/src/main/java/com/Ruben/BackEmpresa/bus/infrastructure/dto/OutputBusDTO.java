package com.Ruben.BackEmpresa.bus.infrastructure.dto;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputBusDTO {
    private String id;
    private final int capacidad = 40;
    private List<Reserva> reservas;
    private String ciudadDestino;
    private Date fechaReserva;
    private Float HoraReserva;

    public OutputBusDTO(Bus bus) {
        setId(bus.getId());
        setReservas(bus.getReservas());
        setCiudadDestino(bus.getCiudadDestino());
        setFechaReserva(bus.getFechaReserva());
        setHoraReserva(bus.getHoraReserva());
    }
}
