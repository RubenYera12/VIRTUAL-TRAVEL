package com.Ruben.BackWeb.reserva.infrastructure.dto;

import com.Ruben.BackWeb.bus.domain.Bus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDisponibleOutputDTO {
    private String ciudadDestino;
    private Date fechaSalida;
    private Float horaSalida;
    private int numeroPlazas;

    public ReservaDisponibleOutputDTO(Bus bus){
        setCiudadDestino(bus.getCiudadDestino());
        setFechaSalida(bus.getFechaReserva());
        setHoraSalida(bus.getHoraReserva());
        setNumeroPlazas(bus.getCapacidad()-bus.getReservas().size());
    }

}
