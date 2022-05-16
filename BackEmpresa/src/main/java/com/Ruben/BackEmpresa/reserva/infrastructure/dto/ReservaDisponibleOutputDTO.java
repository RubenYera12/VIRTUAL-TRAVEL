package com.Ruben.BackEmpresa.reserva.infrastructure.dto;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.DocFlavor;
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
